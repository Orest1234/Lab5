package com.lab5.ui.screens.subjectDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab5.data.entity.SubjectLabEntity
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun SubjectDetailsScreen(
    viewModel: SubjectDetailsViewModel = getViewModel(),
    id: Int
) {
    val subjectState = viewModel.subjectStateFlow.collectAsState()
    val subjectLabsState = viewModel.subjectLabsListStateFlow.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.initData(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Предмет",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = subjectState.value?.title ?: "",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        Text(
            text = "Лабораторні роботи",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 16.dp)
        ) {
            items(subjectLabsState.value) { lab ->
                LabItem(
                    lab = lab,
                    onStatusChange = { updatedLab ->
                        coroutineScope.launch {
                            viewModel.updateSubjectLab(updatedLab)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun LabItem(lab: SubjectLabEntity, onStatusChange: (SubjectLabEntity) -> Unit) {
    var isInProgress by remember { mutableStateOf(lab.inProgress) }
    var isCompleted by remember { mutableStateOf(lab.isCompleted) }
    var comment by remember { mutableStateOf(lab.comment ?: "") }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(isInProgress, isCompleted, comment) {
        onStatusChange(
            lab.copy(
                inProgress = isInProgress,
                isCompleted = isCompleted,
                comment = comment
            )
        )
    }

    Surface(
        shadowElevation = 4.dp,
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Text(
                text = lab.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Text(
                text = lab.description,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Checkbox(
                    checked = isInProgress,
                    onCheckedChange = { checked ->
                        isInProgress = checked
                        if (checked) isCompleted = false
                    }
                )
                Text(text = "В прогресі", fontSize = 14.sp)
            }

            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Checkbox(
                    checked = isCompleted,
                    onCheckedChange = { checked ->
                        isCompleted = checked
                        if (checked) isInProgress = false
                    }
                )
                Text(text = "Завершено", fontSize = 14.sp)
            }

            OutlinedTextField(
                value = comment,
                onValueChange = { newComment -> comment = newComment },
                label = { Text("Коментар до завдання") },
                placeholder = { Text("Напишіть ваш коментар тут...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )

            Button(
                onClick = { focusManager.clearFocus() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Зберегти коментар")
            }
        }
    }
}