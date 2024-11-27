package com.lab5.ui.screens.subjectsList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab5.data.db.Lab5Database
import com.lab5.data.entity.SubjectEntity
import com.lab5.data.entity.SubjectLabEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SubjectsListViewModel(
    private val database: Lab5Database
) : ViewModel() {

    /**
    Flow - the (container, channel, observer), can accept and move data from producer to consumers
    StateFlow - the flow which also store data.
    MutableStateFlow - the stateFlow which can accept data (which you can fill)

    _subjectListStateFlow - private MutableStateFlow - ViewModel (add new data here)
    subjectListStateFlow - public StateFlow - ComposeScreen (read only data on screen)
     */
    private val _subjectListStateFlow = MutableStateFlow<List<SubjectEntity>>(emptyList())
    val subjectListStateFlow: StateFlow<List<SubjectEntity>>
        get() = _subjectListStateFlow


    /**
    Init block of ViewModel - invokes once the ViewModel is created
     */
    init {
        viewModelScope.launch {
            val subjects = database.subjectsDao.getAllSubjects()
            if (subjects.isEmpty()) {
                preloadData()
            } else {
                _subjectListStateFlow.value = subjects
            }
            Log.d("SubjectsListViewModel", "Subjects loaded: ${_subjectListStateFlow.value}")
        }
    }

    private suspend fun preloadData() {
        // List of subjects
        val listOfSubject = listOf(
            SubjectEntity(id = 1, title = "Розробка мобільних додатків"),
            SubjectEntity(id = 2, title = "Адміністрування серверів та хмарних технологій"),
            SubjectEntity(id = 3, title = "Проєктування мережевих інфраструктур"),
            SubjectEntity(id = 4, title = "Інформаційна безпека в ІТ"),
        )
        // List of labs
        val listOfSubjectLabs = listOf(
            SubjectLabEntity(
                id = 1,
                subjectId = 1,
                title = "Вступ до Android",
                description = "Налаштування Android Studio та створення першого проєкту",
                comment = "Дедлайн: 15 листопада",
            ),
            SubjectLabEntity(
                id = 2,
                subjectId = 1,
                title = "Побудова UI в Android",
                description = "Розробка простого додатку для відстеження завдань",
                comment = "Виконано успішно",
                isCompleted = true
            ),
            SubjectLabEntity(
                id = 3,
                subjectId = 2,
                title = "Основи віртуалізації",
                description = "Налаштування віртуальної машини з використанням VirtualBox і Vagrant",
                comment = "Потрібно доопрацювання звіту",
                isCompleted = false
            ),
            SubjectLabEntity(
                id = 4,
                subjectId = 2,
                title = "Контейнеризація з Docker",
                description = "Створення Docker-контейнерів та налаштування базового середовища",
                comment = "Захист заплановано на 20 листопада",
                inProgress = true
            ),
            SubjectLabEntity(
                id = 5,
                subjectId = 3,
                title = "Аналіз вимог до мережі",
                description = "Складання технічного завдання для корпоративної мережі",
                comment = "Чекає перевірки",
                isCompleted = true
            ),
            SubjectLabEntity(
                id = 6,
                subjectId = 3,
                title = "Налаштування VLAN",
                description = "Практичне налаштування віртуальних локальних мереж",
                comment = "Захищено 10 листопада",
                isCompleted = true
            ),
            SubjectLabEntity(
                id = 7,
                subjectId = 4,
                title = "Безпека в мережах",
                description = "Налаштування Syslog, NTP та SSH на маршрутизаторах",
                comment = "Необхідно подати звіт до 25 листопада",
                isCompleted = false
            ),
            SubjectLabEntity(
                id = 8,
                subjectId = 4,
                title = "Презентація з безпеки",
                description = "Підготовка презентації про сучасні мережеві екрани",
                comment = "Здано на відмінно",
                isCompleted = true
            ),
        )

        listOfSubject.forEach { subject ->
            database.subjectsDao.addSubject(subject)
        }
        listOfSubjectLabs.forEach { lab ->
            database.subjectLabsDao.addSubjectLab(lab)
        }
    }
}