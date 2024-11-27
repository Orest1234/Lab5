package com.lab5.ui.screens.subjectsList

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab5.ui.theme.Lab5Theme
import org.koin.androidx.compose.getViewModel

@Composable
fun SubjectsListScreen(
    viewModel: SubjectsListViewModel = getViewModel(),
    onDetailsScreen: (Int) -> Unit,
) {
    val subjectsListState = viewModel.subjectListStateFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Предмети",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 43.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 15.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(subjectsListState.value) { subject ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onDetailsScreen(subject.id) },
                    elevation = CardDefaults.cardElevation(5.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = subject.title,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                }
            }
        }
    }
}
/**
 * Preview can't display data from ViewModel
 */
@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true)
@Composable
private fun SubjectsListScreenPreview() {
    Lab5Theme {
//        SubjectsListScreen({})
    }
}