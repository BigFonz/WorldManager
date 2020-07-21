package com.foenixe.worldmanager.util;

public enum ErrorMessage {

    WORLD_NOT_FOUND(CC.translate("&cWorld not found! Try loading it with /world load <world>")),
    UNLOADED_WORLD_NOT_FOUND(CC.translate("&cUnloaded world not found! Use /world create <name> to generate a world")),
    WORLD_IS_LOADED(CC.translate("&cThat world is already loaded!")),
    WORLD_ALREADY_EXISTS(CC.translate("&cThat world already exists! Please choose another name")),
    WORLD_NAME_TOO_LONG(CC.translate("&cThe world name cannot be longer than 20 characters!")),
    IN_UNLOADING_WORLD(CC.translate("&cYou or other players cannot be in an unloading world! Use /world tp <world> to leave the world")),
    INVALID_ARGUMENTS(CC.translate("&cInvalid arguments! Use /world help for usage")),
    PLAYER_ONLY(CC.translate("&cYou cannot run this through the console!"));

    private String msg;

    ErrorMessage(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }

}
