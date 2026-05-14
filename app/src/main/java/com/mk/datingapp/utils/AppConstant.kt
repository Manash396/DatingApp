package com.mk.datingapp.utils

import com.mk.datingapp.ui.profile.creation.Question

object AppConstant {
    val categories = mapOf(
        "LIFESTYLE" to listOf("Slow Living", "Digital Nomad", "Wellness", "Night Owl", "Minimalism"),
        "ART & CULTURE" to listOf("Contemporary Art", "Poetry", "Cinematography", "Architecture", "Opera"),
        "MUSIC" to listOf("Deep House", "Classical", "Jazz Noir", "Ambient", "Indie Folk"),
        "TRAVEL" to listOf("Hidden Gems", "Solo Expeditions", "Urban Exploration", "Retreats")
    )

    val questions = listOf(
        Question(
            "In a crowded room, where are you most likely to be found?",
            listOf(
                "At the center of it all",
                "In a quiet corner with one person",
                "Near the snacks",
                "Leading the conversation"
            )
        ),
        Question(
            "What’s your ideal weekend?",
            listOf(
                "Exploring outdoors",
                "Watching movies",
                "Hanging with friends",
                "Learning something new"
            )
        ),
        Question(
            "Pick a vibe",
            listOf(
                "Chill & calm",
                "Energetic",
                "Creative",
                "Adventurous"
            )
        )
    )
}