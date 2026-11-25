package com.baquang.lab03_chitietsinhvien

data class Student(
    val id: Int,
    val name: String,
    val major: String,
    val email: String = "",
    val phone: String = ""
)

val sampleStudents = listOf(
    Student(1, "Nguyễn Văn An", "Công nghệ thông tin", "an.nguyen@example.com", "0123 456 789"),
    Student(2, "Trần Thị Bích", "Kỹ thuật phần mềm", "bich.tran@example.com", "0987 654 321"),
    Student(3, "Lê Hoàng Minh", "An toàn thông tin", "minh.le@example.com", "0912 345 678"),
    Student(4, "Phạm Quốc Bảo", "Hệ thống thông tin", "bao.pham@example.com", "0905 111 222")
)

fun findStudentById(id: Int): Student? = sampleStudents.find { it.id == id }