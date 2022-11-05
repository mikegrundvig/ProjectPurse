package com.michaelgrundvig.storage.drawer

sealed class State {
    object Offline : State()
    object Homing : State()
    object Available : State()
    object Moving : State()
}
//
//// offline, homing, available, moving
//
sealed class Event {
    object OnDetection : Event()
    object OnHomingComplete : Event()
    object OnDroppedOffline : Event()
    object OnMovementStart : Event()
    object OnMovementStop : Event()
}
//
sealed class SideEffect {
    object StartHomingProcess : SideEffect()
//    object LogFrozen : SideEffect()
//    object LogVaporized : SideEffect()
//    object LogCondensed : SideEffect()
}
//
//val stateMachine = StateMachine.create {
//    initialState(State.Offline)
//    state<State.Offline> {
//        on<Event.OnDetection> {
//            transitionTo(State.Homing, SideEffect.StartHomingProcess)
//        }
//    }
//    state<State.Homing> {
//        on<Event.OnHomingComplete> {
//            transitionTo(State.Available)
//        }
//        on<Event.OnDroppedOffline> {
//            transitionTo(State.Offline)
//        }
//    }
//    state<State.Available> {
//        on<Event.OnMovementStart> {
//            transitionTo(State.Moving)
//        }
//        on<Event.OnDroppedOffline> {
//            transitionTo(State.Offline)
//        }
//    }
//    state<State.Moving> {
//        on<Event.OnMovementStop> {
//            transitionTo(State.Available)
//        }
//        on<Event.OnDroppedOffline> {
//            transitionTo(State.Offline)
//        }
//    }
//    onTransition {
//        val validTransition = it as? StateMachine.Transition.Valid ?: return@onTransition
//        when (validTransition.sideEffect) {
////            SideEffect.StartHomingProcess -> logger.log(ON_MELTED_MESSAGE)
////            SideEffect.LogFrozen -> logger.log(ON_FROZEN_MESSAGE)
////            SideEffect.LogVaporized -> logger.log(ON_VAPORIZED_MESSAGE)
////            SideEffect.LogCondensed -> logger.log(ON_CONDENSED_MESSAGE)
//        }
//    }
//}
