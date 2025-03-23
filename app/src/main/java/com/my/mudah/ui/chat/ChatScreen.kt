package com.my.mudah.ui.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.my.mudah.model.ChatViewModel

@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val messages by viewModel.messages.collectAsState()
    val inputText = viewModel.messageInput.value
    val showUsers = remember { mutableStateOf(false) }
    val users by remember { viewModel.userList }

    Column(modifier = Modifier.fillMaxSize()) {
        // Toggle Button
        Row(modifier = Modifier.padding(8.dp)) {
            Button(onClick = {
                showUsers.value = !showUsers.value
                if (showUsers.value) viewModel.loadUsers()
            }) {
                Text(if (showUsers.value) "Back to Chat" else "Show Users")
            }
        }

        if (showUsers.value) {
            // ✅ Show user list
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(users, key = { it.id }) { user ->
                    Row(Modifier.padding(8.dp)) {
                        AsyncImage(model = user.avatar, contentDescription = null)
                        Column(Modifier.padding(start = 8.dp)) {
                            Text("${user.firstName} ${user.lastName}")
                            Text(user.email, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        } else {
            // ✅ Show chat
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(messages, key = { it.id }) { message ->
                    ChatBubble(message)
                }
            }

            Row(modifier = Modifier.padding(8.dp)) {
                TextField(
                    value = inputText,
                    onValueChange = { viewModel.messageInput.value = it },
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = {
                    viewModel.sendMessage(inputText)
                    viewModel.messageInput.value = ""
                }) {
                    Text("Send")
                }
            }
        }
    }
}
