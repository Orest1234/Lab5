package com.lab5.ui.screens.subjectDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab5.data.db.Lab5Database
import com.lab5.data.entity.SubjectEntity
import com.lab5.data.entity.SubjectLabEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SubjectDetailsViewModel(
    private val database: Lab5Database,
) : ViewModel() {

    private val _subjectStateFlow = MutableStateFlow<SubjectEntity?>(null)
    val subjectStateFlow: StateFlow<SubjectEntity?>
        get() = _subjectStateFlow

    private val _subjectLabsListStateFlow = MutableStateFlow<List<SubjectLabEntity>>(emptyList())
    val subjectLabsListStateFlow: StateFlow<List<SubjectLabEntity>>
        get() = _subjectLabsListStateFlow

    // Function to fetch Subject and Labs info based on subject ID
    fun initData(id: Int) {
        viewModelScope.launch {
            _subjectStateFlow.value = database.subjectsDao.getSubjectById(id)
            _subjectLabsListStateFlow.value = database.subjectLabsDao.getSubjectLabsBySubjectId(id)
        }
    }

    fun updateSubjectLab(updatedLab: SubjectLabEntity) {
        viewModelScope.launch {
            database.subjectLabsDao.updateSubjectLab(updatedLab)
        }
    }
}