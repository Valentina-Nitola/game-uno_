package com.example.gameuno.model;

public class NamePlayerModel {
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Valida si el nickname es válido (no nulo ni vacío).
     * @return true si el nickname es válido, false si está vacío o solo espacios.
     */
    public boolean isValid() {
        return nickname != null && !nickname.trim().isEmpty();
    }
}
