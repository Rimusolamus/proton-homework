package ch.protonmail.android.protonmailtest.ui.navigation

sealed class NavRoute(val path: String) {

    object AllTasks: NavRoute("allTasks")

    object UpcomingTasks: NavRoute("upcomingTasks")

    object Detail : NavRoute("detail/{id}") {
        fun createRoute(id: String) = "detail/$id"
    }

    object Master: NavRoute("master")

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}


