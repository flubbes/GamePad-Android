package com.gamepad.lib.andengine.entity.particle.emitter;

import com.gamepad.lib.andengine.engine.handler.IUpdateHandler;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 15:48:09 - 01.10.2010
 */
public interface IParticleEmitter extends IUpdateHandler {
	// ===========================================================
	// Final Fields
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public void getPositionOffset(final float[] pOffset);
}
