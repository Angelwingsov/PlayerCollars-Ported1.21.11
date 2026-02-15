package org.jlortiz.playercollars.client;

import io.wispforest.accessories.api.client.AccessoriesRenderStateKeys;
import io.wispforest.accessories.api.client.AccessoryRenderState;
import io.wispforest.accessories.api.client.renderers.AccessoryRenderer;
import io.wispforest.accessories.api.client.rendering.Side;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;

public class PawRenderer implements AccessoryRenderer {

    private static void renderForArm(ItemRenderState stackRenderState, MatrixStack matrices, PlayerEntityModel model, OrderedRenderCommandQueue renderQueue, int light, boolean left) {
        matrices.push();
        AccessoryRenderer.transformToFace(matrices, left ? model.leftArm : model.rightArm, Side.BOTTOM);
        matrices.multiply(new Quaternionf().rotateXYZ((float) Math.PI, (float) (left ? Math.PI : -Math.PI)/ 2, 0));
        matrices.translate(0, -0.1875, -0.125);
        matrices.scale(0.75f, 0.625f, model.thinArms ? 0.875f : 1.03125f);
        stackRenderState.render(matrices, renderQueue, light, OverlayTexture.DEFAULT_UV, 0);
        matrices.pop();
    }

    @Override
    public <S extends LivingEntityRenderState> void render(AccessoryRenderState accessoryState, S entityState, EntityModel<S> entityModel, MatrixStack matrices, OrderedRenderCommandQueue renderQueue) {
        if (!(entityModel instanceof PlayerEntityModel model)) return;

        ItemRenderState stackRenderState = accessoryState.getStateData(AccessoriesRenderStateKeys.ITEM_STACK_STATE);
        Integer light = entityState.getStateData(AccessoriesRenderStateKeys.LIGHT);
        if (stackRenderState == null || light == null) return;

        renderForArm(stackRenderState, matrices, model, renderQueue, light, false);
        renderForArm(stackRenderState, matrices, model, renderQueue, light, true);
    }

    @Override
    public boolean shouldCreateStackRenderState() {
        return true;
    }
}
