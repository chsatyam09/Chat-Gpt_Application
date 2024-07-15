package com.placement.chatbot_gemini_toto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.placement.chatbot_gemini_toto.ui.theme.ColorModelMessage
import com.placement.chatbot_gemini_toto.ui.theme.ColorUserMessage
import com.placement.chatbot_gemini_toto.ui.theme.Purple80
import com.placement.chatbot_gemini_toto.ui.theme.primaryLight


@Composable
fun ChatPage(modifier: Modifier = Modifier,viewModel: ChatViewModel) {
    Column(
        modifier = modifier
    ) {
        AppHeader() //1
        MessageList(
            modifier = Modifier.weight(1f),
            messageList = viewModel.messageList
        )
        MessageInput( //2
            onMessageSend = {
                viewModel.sendMessage(it)
            }
        )
    }
}


@Composable
fun MessageList(modifier: Modifier = Modifier,messageList : List<MessageModel>) {
    if(messageList.isEmpty()){
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.baseline_question_answer_24),
                contentDescription = "Icon",
                tint = Purple80,
            )
            Text(text = "Ask me anything", fontSize = 22.sp)
        }
    }else{
        LazyColumn(
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messageList.reversed()){
                MessageRow(messageModel = it)
            }
        }
    }


}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role=="model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .align(if (isModel) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .clip(RoundedCornerShape(48f))
                    .background(if (isModel) ColorModelMessage else ColorUserMessage)
                    .padding(16.dp)
            ) {

                SelectionContainer {
                    Text(
                        text = messageModel.message,
                        fontWeight = FontWeight.W500,
                        color = Color.White
                    )
                }


            }

        }


    }


}


//------------------------------------------------------
@Composable
fun MessageInput(onMessageSend : (String)-> Unit) {
//    onMessageSend take String and return nothing

    var message by remember { mutableStateOf("")}

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f), // textfield ka size bada nhi ho
            value = message,
            onValueChange = {
                message = it

            },

            textStyle = TextStyle(
                color = Color.DarkGray,
                fontSize = 20.sp
            ),
            label = {
                Text(text = "Notes")
            },
            placeholder = {
                Text(text = "Enter your Notes")
            },
            leadingIcon = {
                Icon(Icons.Default.ArrowForward, contentDescription = "")
            },
            trailingIcon = {
                if (message.isNotEmpty()) {
                    IconButton(
                        onClick = { message = " " }, // Clear the input when clicked
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                    }
                }   // if
                else{
                    // will implement the some logic here
                }
            }, //  trailingIcon
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Done
            ),
        )
        IconButton(onClick = {
            if(message.isNotEmpty()){
                onMessageSend(message) //   onMessageSend will called and passed the message
                message = " " // message will clear out
            }

        }) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send"
            )
        }
    }
}
//------------------------------------------------------
@Composable
fun AppHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(primaryLight)
                .padding(16.dp)

    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Chat G-pt",
            color = Color.White,
            fontSize = 22.sp

        )
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun  AppHeader()  {
//
//    val modifier = Modifier
//    Scaffold(
//
//        topBar = {
//            TopAppBar(
//                title = {
//                    Box(modifier.fillMaxWidth(),
//                        contentAlignment = Alignment.Center
//                    ){
//                        Text(text = "Demo APP")
//                    }
//                }, //   title
//                modifier.padding(6.dp).clip(RoundedCornerShape(12.dp)),
//
//                navigationIcon = {
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
//
//                    }
//                },
//
//
//                actions = {
//                    IconButton(onClick = { /*TODO*/ }) {
//                        BadgedBox(badge ={
//                            Badge(modifier.size(5.dp)){
//
//                            }
//                        } )
//
//                        {
//                            Icon(
//                                imageVector = Icons.Outlined.FavoriteBorder,
//                                contentDescription = " FavoriteBorder",
//                                modifier = Modifier.size(30.dp)
//                            )
//                        }
//
//                    }
//
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(
//                            imageVector = Icons.Default.ShoppingCart,
//                            contentDescription = "ShoppingCart "
//                        )
//                    }
//                }, // action
//                colors = TopAppBarDefaults.smallTopAppBarColors(
//                    containerColor = Color.Yellow
//                )
//
//
//            ) //     Scaffold
//        }
//    )
//
//    { contentPadding -> // Scaffold Started
//
//        Column(
//            modifier
//                .padding(contentPadding)
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(text = "Hello worldsss")
//        }
//
//    }
//
//}

