package com.my.mudah.ui.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my.mudah.model.ChatViewModel

@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val messages by viewModel.messages.collectAsState()
    val inputText = viewModel.messageInput.value

    Column(modifier = Modifier.fillMaxSize()) {
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
            Button(onClick = { viewModel.sendMessage(inputText); viewModel.messageInput.value = "" }) {
                Text("Send")
            }
        }
    }
}