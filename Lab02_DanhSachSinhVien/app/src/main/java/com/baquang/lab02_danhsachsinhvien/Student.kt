package com.baquang.lab02_danhsachsinhvien

data class Student(
    val id: Int,
    val name: String,
    val major: String
)

val sampleStudents = listOf(
    Student(1, "Nguyễn Văn An", "Công nghệ thông tin"),
    Student(2, "Trần Thị Bích", "Kỹ thuật phần mềm"),
    Student(3, "Lê Hoàng Minh", "An toàn thông tin"),
    Student(4, "Phạm Quốc Bảo", "Hệ thống thông tin")
)