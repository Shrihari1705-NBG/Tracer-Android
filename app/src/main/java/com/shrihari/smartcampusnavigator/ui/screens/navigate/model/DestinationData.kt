package com.shrihari.smartcampusnavigator.ui.screens.navigate.model

object DestinationData {

    val destinations = listOf(

        // ===========================
        // Faculty & Staff
        // ===========================

        Destination("Prof. Plasin F Dias", DestinationCategory.FACULTY),
        Destination("Dr. Mahendra M Dixit", DestinationCategory.FACULTY),
        Destination("Mr. A V Kolaki", DestinationCategory.FACULTY),
        Destination("Mr. Nikhil A Kulkarni", DestinationCategory.FACULTY),
        Destination("Mr. Raghavendra Nagaralli", DestinationCategory.FACULTY),
        Destination("Mrs. Rohini Kallur", DestinationCategory.FACULTY),
        Destination("Mr. Suraj Kadli", DestinationCategory.FACULTY),
        Destination("Mrs. Vijayalaxmi C Kalal", DestinationCategory.FACULTY),
        Destination("Ms. Pooja C Shidhe", DestinationCategory.FACULTY),
        Destination("Mr. Sudheendra Yalagur", DestinationCategory.FACULTY),
        Destination("Ms. Pavitra M Badiger", DestinationCategory.FACULTY),
        Destination("Mrs. Ashwini Garaddi", DestinationCategory.FACULTY),
        Destination("Mrs. Rajeshwari Pashupatimath", DestinationCategory.FACULTY),
        Destination("Mrs. Jyothi Kammar", DestinationCategory.FACULTY),
        Destination("Dr. Meenal M Kaliwal", DestinationCategory.FACULTY),
        Destination("Dr. Gururaj Hatti", DestinationCategory.FACULTY),

        // ===========================
        // Laboratories
        // ===========================

        Destination("Analog Lab", DestinationCategory.LABORATORY),
        Destination("DSP Lab", DestinationCategory.LABORATORY),
        Destination("DC Lab", DestinationCategory.LABORATORY),
        Destination("Research Lab", DestinationCategory.LABORATORY),

        // ===========================
        // Classrooms
        // ===========================

        Destination("Block 6(A)", DestinationCategory.CLASSROOM),
        Destination("Block 6 (Smart Room)", DestinationCategory.CLASSROOM),

        // ===========================
        // Offices
        // ===========================

        Destination("HOD ECE", DestinationCategory.OFFICE),

        // ===========================
        // Facilities
        // ===========================

        Destination("Department Library", DestinationCategory.FACILITY),
        Destination("Ladies Room", DestinationCategory.FACILITY),
        Destination("Ladies Washroom", DestinationCategory.FACILITY)
    )
}