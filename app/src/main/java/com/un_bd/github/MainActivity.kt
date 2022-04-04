package com.un_bd.github

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.un_bd.github.ui.screen.ReposScreen
import com.un_bd.github.ui.screen.UsersScreen
import com.un_bd.github.ui.theme.GitHubTheme
import com.un_bd.github.viewmodel.ReposViewModel
import com.un_bd.github.viewmodel.UsersViewModel


class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      GitHubTheme(darkTheme = false) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          BoxWithConstraints {
            NavigationScreen(constraints.maxWidth / 2)
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationScreen(width: Int) {
  val navController = rememberAnimatedNavController()

  /*AnimatedNavHost(navController = navController, startDestination = Screen.UsersScreen.route) {
    composable(route = Screen.UsersScreen.route,
      enterTransition = {
        slideIntoContainer(
          AnimatedContentScope.SlideDirection.Left,
          animationSpec = tween(durationMillis)
        )
      },
      exitTransition = {
        slideOutOfContainer(
          AnimatedContentScope.SlideDirection.Left,
          animationSpec = tween(durationMillis)
        )
      },
      popEnterTransition = {
        slideIntoContainer(
          AnimatedContentScope.SlideDirection.Right,
          animationSpec = tween(durationMillis)
        )
      },
      popExitTransition = {
        slideOutOfContainer(
          AnimatedContentScope.SlideDirection.Right,
          animationSpec = tween(durationMillis)
        )
      }) {
      UsersScreen(usersViewModel = viewModel(factory = UsersViewModel.provideFactory())) {
        navController.navigate("${Screen.ReposScreen.route}/$it") {
          popUpTo(Screen.UsersScreen.route)
        }
      }
    }

    composable(route = "${Screen.ReposScreen.route}/{user}",
      enterTransition = {
        slideIntoContainer(
          AnimatedContentScope.SlideDirection.Left,
          animationSpec = tween(durationMillis)
        )
      },
      exitTransition = {
        slideOutOfContainer(
          AnimatedContentScope.SlideDirection.Left,
          animationSpec = tween(durationMillis)
        )
      },
      popEnterTransition = {
        slideIntoContainer(
          AnimatedContentScope.SlideDirection.Right,
          animationSpec = tween(durationMillis)
        )
      },
      popExitTransition = {
        slideOutOfContainer(
          AnimatedContentScope.SlideDirection.Right,
          animationSpec = tween(durationMillis)
        )
      }
    ) {
      it.arguments?.getString("user")?.let { user ->
        ReposScreen(
          reposViewModel = viewModel(factory = ReposViewModel.provideFactory(user = user)), user
        ) {
          navController.popBackStack()
        }
      }
    }
  }*/

  AnimatedNavHost(navController = navController, startDestination = Screen.UsersScreen.route) {
    composable(route = Screen.UsersScreen.route,
      enterTransition = {
        slideInHorizontally(
          initialOffsetX = { width },
          animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
          )
        ) + fadeIn(animationSpec = tween(300))
      },
      exitTransition = {
        slideOutHorizontally(
          targetOffsetX = { -width },
          animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
          )
        ) + fadeOut(animationSpec = tween(300))
      },
      popEnterTransition = {
        slideInHorizontally(
          initialOffsetX = { -width },
          animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
          )
        ) + fadeIn(animationSpec = tween(300))
      },
      popExitTransition = {
        slideOutHorizontally(
          targetOffsetX = { width },
          animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
          )
        ) + fadeOut(animationSpec = tween(300))
      }) {
      //(usersViewModel = viewModel(factory = UsersViewModel.provideFactory()))
      UsersScreen(usersViewModel = viewModel(factory = UsersViewModel.provideFactory())) {
        navController.navigate("${Screen.ReposScreen.route}/$it") {
          popUpTo(Screen.UsersScreen.route)
        }
      }
    }

    composable(route = "${Screen.ReposScreen.route}/{user}",
      enterTransition = {
        slideIntoContainer(
          AnimatedContentScope.SlideDirection.Left,
          animationSpec = tween(durationMillis)
        )
      },
      exitTransition = {
        slideOutOfContainer(
          AnimatedContentScope.SlideDirection.Left,
          animationSpec = tween(durationMillis)
        )
      },
      popEnterTransition = {
        slideIntoContainer(
          AnimatedContentScope.SlideDirection.Right,
          animationSpec = tween(durationMillis)
        )
      },
      popExitTransition = {
        slideOutOfContainer(
          AnimatedContentScope.SlideDirection.Right,
          animationSpec = tween(durationMillis)
        )
      }
    ) {
      it.arguments?.getString("user")?.let { user ->
        ReposScreen(
          reposViewModel = viewModel(factory = ReposViewModel.provideFactory(user = user)), user
        ) {
          navController.popBackStack()
        }
      }
    }
  }

}

const val durationMillis: Int = 300

sealed class Screen(val route: String) {
  object UsersScreen : Screen("UsersScreen")
  object ReposScreen : Screen("ReposScreen")

}











