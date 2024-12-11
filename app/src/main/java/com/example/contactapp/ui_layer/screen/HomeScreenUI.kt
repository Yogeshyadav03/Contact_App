package com.example.contactapp.ui_layer.screen

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.contactapp.ui_layer.viewModel.ContactAppViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.contactapp.ui_layer.navigation.AddEditScreen
import com.example.contactapp.ui_layer.state.ContactState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenUI(
    navController: NavController,
    viewModel: ContactAppViewModel = hiltViewModel(),
    state: ContactState
) {
//  val state = viewModel.state.collectAsState()
    val context = LocalContext.current
    val activity = context as Activity


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(AddEditScreen) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(it)
        ) {
            items(state.contactList) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .combinedClickable(
                            onClick = {
//                                navController.navigate(AddEditScreen)
                            },
                            onDoubleClick = {
                                state.id.value = it.id
                                state.name.value = it.name
                                state.email.value = it.email
                                state.phoneNumber.value = it.phoneNumber
                                state.dob.value = it.dob
                                state.image.value = it.image
                                navController.navigate(AddEditScreen)

                                Log.d("TAG", "HomeScreenUI: ${it.id}")

                            },
                            onLongClick = {
                                val callIntent = Intent(Intent.ACTION_DIAL)
                                callIntent.setData(Uri.parse("tel:${it.phoneNumber}"))

                                context.startActivity(callIntent)
                            }

                        ),

                    ) {
                    /*Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {*/
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        if (it.image == null) {

                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.shapes.medium
                                    ),

                                contentAlignment = Alignment.Center
                            ) {
                                val test = it.name.split(" ", ignoreCase = false)
                                val test2 = test.filter { it.isNotBlank() }
                                val abc = test2.map { it.first() }
                                Text(
                                    text = abc.joinToString(separator = ""),

                                    color = MaterialTheme.colorScheme.onPrimary,
                                    style = MaterialTheme.typography.bodyMedium // Align text with the center of the row
                                )
                            }

                        } else {
                            Image(
                                bitmap = BitmapFactory.decodeByteArray(
                                    it.image,
                                    0,
                                    it.image.size!!
                                ).asImageBitmap(), contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .aspectRatio(1f)
                                    .clip(CircleShape)

                            )

                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 10.dp)
                        ) {
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = it.phoneNumber,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                            )
                            Text(
                                text = it.email,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                            )
                        }
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = null,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    viewModel.state.value.id.value = it.id
                                    viewModel.state.value.name.value = it.name
                                    viewModel.state.value.email.value = it.email
                                    viewModel.state.value.phoneNumber.value = it.phoneNumber
                                    viewModel.state.value.dob.value = it.dob
                                    viewModel.state.value.image.value = it.image
                                    Log.d("TAG", "HomeScreenUI: ${it.id}")

                                    viewModel.deleteContact()
                                },
                            tint = MaterialTheme.colorScheme.error

                        )
                    }
                }
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}
