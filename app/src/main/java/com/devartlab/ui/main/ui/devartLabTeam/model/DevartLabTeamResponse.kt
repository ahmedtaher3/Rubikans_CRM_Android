package com.devartlab.ui.main.ui.devartLabTeam.model

data class DevartLabTeamResponse(
    val _id: String,
    val created_at: String,
    val description: String,
    val image: String,
    val name: String,
    val parent_id: String,
    val sub: List<Sub>,
    val team: List<Team>,
    val updated_at: String
)