package com.example.contactapp.ui_layer.screen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.contactapp.R
import com.example.contactapp.ui_layer.state.ContactState
import com.example.contactapp.ui_layer.viewModel.ContactAppViewModel


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AddEditScreenUI(
    navController: NavController,
    viewModel: ContactAppViewModel = hiltViewModel(),
    state: ContactState,
    onEvent: () -> Unit


) {


    val context = LocalContext.current
  //  val state = viewModel.state.collectAsState()

    Log.d("TAG", "AddEditScreenUI: ${state.id}")
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            if (it != null) {
                val inputStream = context.contentResolver.openInputStream(it)
                val byte = inputStream?.readBytes()
                state.image.value = byte
            }

        }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()

    ) {


        if (state.image.value == null) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(100.dp).clickable {
                    launcher.launch("image/*")
                }
            )
        } else {
            Image(
                bitmap = BitmapFactory.decodeByteArray(
                    state.image.value,
                    0,
                    state.image.value!!.size
                ).asImageBitmap(), contentDescription = null,
                modifier = Modifier.size(100.dp).clickable {
                    launcher.launch("image/*")
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = state.name.value,
            onValueChange = {
                state.name.value = it
            },
            shape = RoundedCornerShape(20.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Magenta,
                unfocusedContainerColor = Color.LightGray
            ),
            placeholder = { Text(text = "Name") },
            label = { Text(text = "Name ") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )

        Spacer(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = state.phoneNumber.value,
            onValueChange = {
                state.phoneNumber.value = it
            },
            placeholder = { Text(text = "Phone Number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(value = state.email.value, onValueChange = {
            state.email.value = it
        }, placeholder = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(
            modifier = Modifier
                .height(30.dp)
                .fillMaxWidth()
        )
        Button(
            onClick = {
              //  viewModel.upsertContact()
                onEvent.invoke()
                viewModel.clearState()
                navController.navigateUp()
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green, contentColor = Color.Blue
            ), elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 10.dp, pressedElevation = 30.dp
            )
        ) {
            Text(text = "Save Contact")
        }
    }
}
