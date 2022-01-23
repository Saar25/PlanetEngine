package org.saar.minecraft.entity;

import org.saar.core.camera.Camera;
import org.saar.minecraft.BlockFaceContainer;
import org.saar.minecraft.World;

public class Player {

    private final Camera camera;

    public Player(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public BlockFaceContainer rayCast(World world) {
        return RayCasting.lookingAtFace(camera, world, 16);
    }
}
