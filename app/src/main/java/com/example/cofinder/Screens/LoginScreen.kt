package com.example.cofinder.Screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cofinder.Navigation.Routes
import com.example.cofinder.R

@Composable
fun LoginScreen(navController: NavHostController) {
//    val navViewModel: NavViewModel =
//        viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)

    val context = LocalContext.current
    val activity = context as Activity
//    val authManager = AuthManager(activity)

    var userID by remember {
        mutableStateOf("")
    }

    var userPasswd by remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val fontFamily = FontFamily(
        fonts = listOf(
            Font(R.font.gmarket_sans_ttf_medium, FontWeight.Medium),
            Font(R.font.gmarket_sans_ttf_bold, FontWeight.Bold),
            Font(R.font.gmarket_sans_ttf_light, FontWeight.Light)
        )
    )


    val buttonColor1 = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.darkgreen),
        contentColor = Color.White,
        disabledContainerColor = colorResource(id = R.color.middlegreen),
        disabledContentColor = Color.White
    )

    val buttonColor2 = ButtonDefaults.buttonColors(
        containerColor = colorResource(R.color.greengray),
        contentColor = Color.White,
        disabledContainerColor = colorResource(id = R.color.greengray),
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

    Scaffold(topBar = { Text(text = "CO-Finder",
        modifier = Modifier.padding(16.dp),
        fontSize = 28.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.ExtraBold,
        color = colorResource(id = R.color.darkgreen))}) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            Text("로그인", fontSize = 36.sp, fontFamily=fontFamily,modifier = Modifier.padding(8.dp),
                color = colorResource(id = R.color.darkgreen))
            Text("로그인 후 Co-Finder의 다양한 서비스를 이용하세요!", fontSize = 10.sp, fontFamily=fontFamily,
                modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 16.dp), color = colorResource(id = R.color.lightgreen))
            OutlinedTextField(
                value = userID,
                onValueChange = {userID = it},
                modifier = Modifier.padding(bottom = 12.dp),
                colors = textFieldColors,
                shape = RoundedCornerShape(20.dp),
                label = {
                    Text(text = "아이디를 입력해주세요.",
                        color = colorResource(id = R.color.darkgreen),
                        fontFamily = fontFamily)
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
                        fontFamily = fontFamily
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
            Button(modifier = Modifier.padding(12.dp).width(280.dp),
                colors = buttonColor1,
                onClick = {

                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Home.route) {
                            saveState = true
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {
                Text("로그인", fontFamily = fontFamily, modifier = Modifier.padding(6.dp))
            }
            Button(modifier = Modifier.padding(6.dp).width(280.dp),
                colors = buttonColor2,
                onClick = { /*TODO*/ }) {
                Text("회원가입", fontFamily = fontFamily, modifier = Modifier.padding(6.dp))
            }

        }
    }

}
