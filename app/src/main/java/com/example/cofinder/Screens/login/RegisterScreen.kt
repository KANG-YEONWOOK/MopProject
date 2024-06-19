package com.example.cofinder.Screens.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cofinder.Bars.RegisterTopBar
import com.example.cofinder.Data.UserData
import com.example.cofinder.Navigation.Routes
import com.example.cofinder.R
import com.example.cofinder.Repository.UserRepository
import com.example.cofinder.Repository.UserService
import com.example.cofinder.Repository.UserViewModel
import com.example.cofinder.Repository.UserViewModelFactory
import com.example.cofinder.ui.theme.Typography
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun RegisterScreen(navController: NavController, userViewModel: UserViewModel) {

    Scaffold(topBar = { RegisterTopBar(navController = navController)}){
        RegisterScreenContent(navController, userViewModel, contentPadding = it)
    }
}
private const val BASE_URL = "http://192.168.175.12:9090/"
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private val userService = retrofit.create(UserService::class.java)
@Composable
fun RegisterScreenContent(navController: NavController, userviewModel: UserViewModel, contentPadding:PaddingValues) {


    val coroutineScope = rememberCoroutineScope()
    var userID by remember {
        mutableStateOf("")
    }

    var userPasswd by remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val buttonColor1 = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.darkgreen),
        contentColor = Color.White,
        disabledContainerColor = colorResource(id = R.color.middlegreen),
        disabledContentColor = Color.White
    )

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(id = R.color.darkgreen),
        unfocusedBorderColor = colorResource(id = R.color.middlegreen),
        cursorColor = colorResource(id = R.color.darkgreen),
        focusedContainerColor = colorResource(id = R.color.highlightgreen),
        unfocusedContainerColor = colorResource(id = R.color.highlightgreen),
        focusedTextColor = colorResource(id = R.color.darkgreen),
        unfocusedTextColor = colorResource(id = R.color.darkgreen)
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(contentPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("회원가입", style = Typography.titleLarge ,modifier = Modifier.padding(8.dp),
            color = colorResource(id = R.color.darkgreen))
        Text("가입하실 아이디와 비밀번호를 입력해주세요!", style = Typography.bodySmall,
            modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 12.dp), color = colorResource(id = R.color.lightgreen))
        OutlinedTextField(
            value = userID,
            onValueChange = {userID = it},
            modifier = Modifier.padding(bottom = 12.dp),
            colors = textFieldColors,
            shape = RoundedCornerShape(20.dp),
            label = {
                Text(text = "아이디를 입력해주세요.",
                    color = colorResource(id = R.color.darkgreen),
                    style = Typography.bodyMedium)
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            })
        )
        OutlinedTextField(
            colors = textFieldColors,
            modifier = Modifier.padding(bottom = 24.dp),
            shape = RoundedCornerShape(20.dp),
            value = userPasswd,
            onValueChange = { userPasswd = it },
            label = {
                Text(
                    text = "비밀번호를 입력해주세요.",
                    color = colorResource(id = R.color.darkgreen),
                    style = Typography.bodyMedium
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            })
        )
        Button(modifier = Modifier
            .padding(12.dp)
            .width(280.dp),
            colors = buttonColor1,
            onClick = {
                coroutineScope.launch {
                    try {
                        val newUser = UserData(
                            username = "newUser23",
                            password = "securePassword12345",
                            email = "newuser21@example.com"
                        )
                        val createdUser = userService.registerUser(newUser)
                        createdUser.enqueue(object : Callback<Void>{
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                if (response.isSuccessful) {
                                    println("User registered successfully")
                                } else {
                                    println("Request failed: ${response.code()}")
                                }

                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                t.printStackTrace()
                            }
                        })
                        // 사용자 정보 저장 성공 시 다음 화면으로 이동
                        navController.navigate(Routes.Login.route) {
//                            popUpTo(Routes.Login.route) {
//                                saveState = true
//                                inclusive = false
//                            }
//                            launchSingleTop = true
//                            restoreState = true
                        }
                    } catch (e: Exception) {
                        // 사용자 정보 저장 실패 시 오류 처리
                        Log.e("UserRegistration", "Error creating user: $e")
                    }
                }
            }) {
            Text("회원가입", style = Typography.bodyMedium, modifier = Modifier.padding(6.dp))
        }

    }
}