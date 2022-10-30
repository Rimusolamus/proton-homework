package ch.protonmail.android.protonmailtest.ui.navigation

sealed class NavRoute(val path: String) {

    object Detail : NavRoute("detail/{id}") {
        fun createRoute(id: String) = "detail/$id"
    }

    object Master : NavRoute("master")
}


