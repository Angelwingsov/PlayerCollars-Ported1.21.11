package org.jlortiz.playercollars.client;

import io.wispforest.accessories.api.client.AccessoriesRenderStateKeys;
import io.wispforest.accessories.api.client.AccessoryRenderState;
import io.wispforest.accessories.api.client.renderers.AccessoryRenderer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;

public class CollarRenderer implements AccessoryRenderer {

    @Override
    public <S extends LivingEntityRenderState> void render(AccessoryRenderState accessoryState, S entityState, EntityModel<S> entityModel, MatrixStack matrixStack, OrderedRenderCommandQueue renderQueue) {
        if (!(entityModel instanceof PlayerEntityModel playerModel)) return;

        ItemRenderState stackRenderState = accessoryState.getStateData(AccessoriesRenderStateKeys.ITEM_STACK_STATE);
        Integer light = entityState.getStateData(AccessoriesRenderStateKeys.LIGHT);
        if (stackRenderState == null || light == null) return;

        ModelPart body = playerModel.body;
        matrixStack.push();
        AccessoryRenderer.transformToModelPart(matrixStack, body);
        matrixStack.scale(0.85f, 0.85f, 0.85f);
        matrixStack.translate(0, -0.01, -0.005);
        stackRenderState.render(matrixStack, renderQueue, light, OverlayTexture.DEFAULT_UV, 0);
        matrixStack.pop();
    }

    @Override
    public boolean shouldCreateStackRenderState() {
        return true;
    }
}
