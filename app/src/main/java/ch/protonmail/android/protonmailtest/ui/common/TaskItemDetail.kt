package ch.protonmail.android.protonmailtest.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ch.protonmail.android.protonmailtest.R
import ch.protonmail.android.protonmailtest.model.Task
import ch.protonmail.android.protonmailtest.ui.theme.DarkGrey
import ch.protonmail.android.protonmailtest.ui.theme.Grey
import ch.protonmail.android.protonmailtest.ui.theme.Purple
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun TaskItemDetail(task: Task) {
    Row(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(
                text = task.encryptedTitle,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = task.encryptedDescription,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.body2,
                color = Grey
            )
            Row {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.CenterVertically),
                            colorFilter = ColorFilter.tint(Purple)
                        )
                        Text(
                            text = "Created:",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.body2,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 9.dp) // ??????
                        )
                    }
                    Row(modifier = Modifier.padding(top = 8.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_bell),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Purple),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Text(
                            text = "Due:",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.body2,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 9.dp)
                        )
                    }
                }
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(
                        text = task.creationDate,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        text = task.dueDate,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                    )
                }
            }
        }
    }
}