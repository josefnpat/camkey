package com.camkey.keyframe;

import java.util.ArrayList;
import java.util.List;

public final class Keyframes {
    public static final String START = "@start";
    public static final String END = "@end";

    private final List<Keyframe> keyframes = new ArrayList<>();

    public List<Keyframe> all() {
        return List.copyOf(keyframes);
    }

    public List<String> names() {
        return keyframes.stream().map(Keyframe::name).toList();
    }

    public boolean contains(String name) {
        return find(name) != null;
    }

    public Keyframe find(String name) {
        return keyframes.stream()
            .filter(keyframe -> keyframe.name().equals(name))
            .findFirst()
            .orElse(null);
    }

    public void add(Keyframe keyframe) {
        keyframes.add(keyframe);
    }

    public void replaceAll(List<Keyframe> loaded) {
        keyframes.clear();
        keyframes.addAll(loaded);
    }

    public boolean remove(String name) {
        return keyframes.remove(find(name));
    }

    public void rename(String name, String newName) {
        int index = keyframes.indexOf(find(name));
        keyframes.set(index, keyframes.get(index).withName(newName));
    }

    public void moveAfter(String name, String target) {
        Keyframe keyframe = find(name);
        keyframes.remove(keyframe);
        int index;
        if (START.equals(target)) {
            index = 0;
        } else if (END.equals(target)) {
            index = keyframes.size();
        } else {
            index = keyframes.indexOf(find(target)) + 1;
        }
        keyframes.add(index, keyframe);
    }
}
