package com.fadtech.challengechap6kel1.enum

enum class GameMechanic(val mechanic: Int) {
    IDDLE(-1),
    ROCK(0),
    SCISSOR(1),
    PAPER(2);

    companion object {
        fun formInt(mechanic: Int) = values().first() { it.mechanic == mechanic }
    }
}