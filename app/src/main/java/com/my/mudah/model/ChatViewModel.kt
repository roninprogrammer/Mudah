package com.my.mudah.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.mudah.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import okhttp3.ResponseBody
import org.json.JSONObject

@HiltViewModel
class ChatViewModel @Inject constructor(private val repo: ChatRepository) : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages = _messages.asStateFlow()
    val messageInput = mutableStateOf("")

    private var lastSentTime = System.currentTimeMillis()

    init {
        loadUsers()
        loadMessages()
        observeInactivity()
    }

    private fun loadMessages() {
        repo.getMessages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _messages.value = it
            }
    }

    fun sendMessage(input: String) {
        val msg = ChatMessage(
            id = 0,
            timestamp = System.currentTimeMillis(),
            direction = "OUTGOING",
            message = input
        )
        lastSentTime = System.currentTimeMillis()

        repo.sendMessageToApi(msg).subscribe({ response ->
            val responseBody = response.body()?.toString() ?: return@subscribe

            val json = JSONObject(responseBody)
            val chatArray = json.optJSONArray("chat")
            if (chatArray != null) {
                for (i in 0 until chatArray.length()) {
                    val item = chatArray.getJSONObject(i)
                    val message = ChatMessage(
                        timestamp = item.getLong("timestamp"),
                        direction = item.getString("direction"),
                        message = item.getString("message")
                    )
                    repo.insertMessage(message)
                }
            } else {
                // fallback: just insert the typed message
                repo.insertMessage(msg)
            }

        }, {
            Log.e("ChatViewModel", "API Error", it)
        })
    }

    private fun observeInactivity() {
        viewModelScope.launch {
            while (true) {
                delay(60000)
                if (System.currentTimeMillis() - lastSentTime >= 60000) {
                    val systemMsg = ChatMessage(
                        id = 0,
                        timestamp = System.currentTimeMillis(),
                        direction = "INCOMING",
                        message = "Are you there?"
                    )
                    repo.insertMessage(systemMsg)
                }
            }
        }
    }

    val userList = mutableStateOf<List<User>>(emptyList())

    fun loadUsers() {
        repo.getUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ users ->
                userList.value = users
                Log.d("ChatViewModel", "Fetched users: ${users.size}")
            }, {
                Log.e("ChatViewModel", "Error fetching users", it)
            })
    }

}