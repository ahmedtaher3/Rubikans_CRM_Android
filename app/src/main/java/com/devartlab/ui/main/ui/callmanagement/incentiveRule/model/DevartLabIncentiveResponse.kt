package com.devartlab.ui.main.ui.callmanagement.incentiveRule.model
data class DevartLabIncentiveResponse(
    val _id: String,
    val created_at: String,
    val description: String,
    val image: String,
    val name: String,
    val parent_id: String,
    val sub: List<Sub>,
    val incentive: List<Incentive>,
    val updated_at: String
)