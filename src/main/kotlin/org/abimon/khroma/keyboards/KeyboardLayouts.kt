package org.abimon.khroma.keyboards

import org.abimon.khroma.keyboards.KhromaFrame.Companion.set

object KeyboardLayouts {
    val enUS: Map<Key, Pair<Int, Int>> = HashMap<Key, Pair<Int, Int>>().apply {
        this[Key.ESCAPE] = 1 to 0
        this[Key.F1] = 3 to 0
        this[Key.F2] = 4 to 0
        this[Key.F3] = 5 to 0
        this[Key.F4] = 6 to 0
        this[Key.F5] = 7 to 0
        this[Key.F6] = 8 to 0
        this[Key.F7] = 9 to 0
        this[Key.F8] = 10 to 0
        this[Key.F9] = 11 to 0
        this[Key.F10] = 12 to 0
        this[Key.F11] = 13 to 0
        this[Key.F12] = 14 to 0
        this[Key.PRINT_SCREEN] = 15 to 0
        this[Key.SCROLL_LOCK] = 16 to 0
        this[Key.PAUSE] = 17 to 0

        this[Key.TILDE, Key.GRAVE] = 1 to 1
        this[Key.NUM_1, Key.EXCLAMATION_MARK] = 2 to 1
        this[Key.NUM_2, Key.AT] = 3 to 1
        this[Key.NUM_3, Key.NUMBER_SIGN] = 4 to 1
        this[Key.NUM_4, Key.DOLLAR_SIGN] = 5 to 1
        this[Key.NUM_5, Key.PERCENTAGE_SIGN] = 6 to 1
        this[Key.NUM_6, Key.CARET] = 7 to 1
        this[Key.NUM_7, Key.AMPERSAND] = 8 to 1
        this[Key.NUM_8, Key.ASTERISK] = 9 to 1
        this[Key.NUM_9, Key.LEFT_PARENTHESIS] = 10 to 1
        this[Key.NUM_0, Key.RIGHT_PARENTHESIS] = 11 to 1
        this[Key.MINUS, Key.UNDERSCORE] = 12 to 1
        this[Key.EQUALS, Key.PLUS] = 13 to 1
        this[Key.BACKSPACE] = 14 to 1
        this[Key.INSERT] = 15 to 1
        this[Key.HOME] = 16 to 1
        this[Key.PAGE_UP] = 17 to 1
        this[Key.NUM_LOCK] = 18 to 1
        this[Key.NUMPAD_SLASH] = 19 to 1
        this[Key.NUMPAD_ASTERISK] = 20 to 1
        this[Key.NUMPAD_MINUS] = 21 to 1

        this[Key.TAB] = 1 to 2
        this[Key.Q] = 2 to 2
        this[Key.W] = 3 to 2
        this[Key.E] = 4 to 2
        this[Key.R] = 5 to 2
        this[Key.T] = 6 to 2
        this[Key.Y] = 7 to 2
        this[Key.U] = 8 to 2
        this[Key.I] = 9 to 2
        this[Key.O] = 10 to 2
        this[Key.P] = 11 to 2
        this[Key.LEFT_BRACE, Key.LEFT_BRACKET] = 12 to 2
        this[Key.RIGHT_BRACE, Key.RIGHT_BRACKET] = 13 to 2
        this[Key.BACKSLASH, Key.VERTICAL_BAR] = 14 to 2
        this[Key.DELETE] = 15 to 2
        this[Key.END] = 16 to 2
        this[Key.PAGE_DOWN] = 17 to 2
        this[Key.NUMPAD_7, Key.NUMPAD_HOME] = 18 to 2
        this[Key.NUMPAD_8, Key.NUMPAD_UP] = 19 to 2
        this[Key.NUMPAD_9, Key.NUMPAD_PAGE_UP] = 20 to 2
        this[Key.NUMPAD_PLUS] = 21 to 2

        this[Key.CAPSLOCK] = 1 to 3
        this[Key.A] = 2 to 3
        this[Key.S] = 3 to 3
        this[Key.D] = 4 to 3
        this[Key.F] = 5 to 3
        this[Key.G] = 6 to 3
        this[Key.H] = 7 to 3
        this[Key.J] = 8 to 3
        this[Key.K] = 9 to 3
        this[Key.L] = 10 to 3
        this[Key.COLON, Key.SEMICOLON] = 11 to 3
        this[Key.APOSTROPHE, Key.QUOTATION_MARKS] = 12 to 3
        this[Key.ENTER] = 14 to 3
        this[Key.NUMPAD_4, Key.NUMPAD_LEFT] = 18 to 3
        this[Key.NUMPAD_5] = 19 to 3
        this[Key.NUMPAD_6, Key.NUMPAD_RIGHT] = 20 to 3

        this[Key.LEFT_SHIFT] = 1 to 4
        this[Key.Z] = 3 to 4
        this[Key.X] = 4 to 4
        this[Key.C] = 5 to 4
        this[Key.V] = 6 to 4
        this[Key.B] = 7 to 4
        this[Key.N] = 8 to 4
        this[Key.M] = 9 to 4
        this[Key.COMMA, Key.LESS_THAN] = 10 to 4
        this[Key.PERIOD, Key.GREATER_THAN] = 11 to 4
        this[Key.SLASH, Key.QUESTION_MARK] = 12 to 4
        this[Key.RIGHT_SHIFT] = 14 to 4
        this[Key.UP] = 16 to 4
        this[Key.NUMPAD_1, Key.NUMPAD_END] = 18 to 4
        this[Key.NUMPAD_2, Key.NUMPAD_DOWN] = 19 to 4
        this[Key.NUMPAD_3, Key.NUMPAD_PAGE_DOWN] = 20 to 4
        this[Key.NUMPAD_ENTER] = 21 to 4

        this[Key.LEFT_CONTROL] = 1 to 5
        this[Key.LEFT_WINDOWS] = 2 to 5
        this[Key.LEFT_ALT] = 3 to 5
        this[Key.SPACE] = 7 to 5
        this[Key.RIGHT_ALT] = 11 to 5
        this[Key.FUNCTION] = 12 to 5
        this[Key.APPLICATION] = 13 to 5
        this[Key.RIGHT_CONTROL] = 14 to 5
        this[Key.LEFT] = 15 to 5
        this[Key.DOWN] = 16 to 5
        this[Key.RIGHT] = 17 to 5
        this[Key.NUMPAD_0, Key.NUMPAD_INSERT] = 19 to 5
        this[Key.NUMPAD_PERIOD, Key.NUMPAD_DELETE] = 20 to 5
    }
}