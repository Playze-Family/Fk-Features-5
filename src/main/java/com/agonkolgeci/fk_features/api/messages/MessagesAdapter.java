package com.agonkolgeci.fk_features.api.messages;

import com.agonkolgeci.fk_features.utils.objects.ObjectUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jetbrains.annotations.NotNull;

public interface MessagesAdapter {

    @NotNull Component getLabel();

    @NotNull
    default Component text(@NotNull Component component) {
        return custom(getLabel(), component);
    }

    @NotNull
    static Component custom(@NotNull Component label, @NotNull Component text) {
        return Component.empty().append(label.decorate(TextDecoration.BOLD)).appendSpace().append(Component.text("•", NamedTextColor.DARK_GRAY)).appendSpace().append(text).colorIfAbsent(NamedTextColor.GRAY);
    }

    @NotNull Sound SOUND_INFO = Sound.sound(Key.key("note.pling"), Sound.Source.BLOCK, 1F, 2F);
    @NotNull Sound SOUND_ERROR = Sound.sound(Key.key("mob.villager.no"), Sound.Source.NEUTRAL, 1F, 1F);

    default void sendMessage(@NotNull Audience audience, @NotNull Component component, @NotNull Sound sound) {
        audience.sendMessage(text(component));
        audience.playSound(sound);
    }

    default void sendInfo(@NotNull Audience audience, @NotNull Component component) {
        sendMessage(audience, component, SOUND_INFO);
    }

    default void sendError(@NotNull Audience audience, @NotNull Exception exception) {
        sendMessage(audience, Component.text(ObjectUtils.requireNonNullElse(exception.getMessage(), "Une erreur inconnue s'est produite, veuillez contactez l'administration."), NamedTextColor.RED), SOUND_ERROR);
    }
}
