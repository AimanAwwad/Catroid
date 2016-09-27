/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2016 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import org.catrobat.catroid.R;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.utils.Utils;

import java.util.List;

public class TurnLeftBrick extends FormulaBrick {

	private static final long serialVersionUID = 1L;

	private transient View prototypeView;

	public TurnLeftBrick() {
		addAllowedBrickField(BrickField.TURN_LEFT_DEGREES);
	}

	public TurnLeftBrick(double degreesValue) {
		initializeBrickFields(new Formula(degreesValue));
	}

	public TurnLeftBrick(Formula degrees) {
		initializeBrickFields(degrees);
	}

	private void initializeBrickFields(Formula degrees) {
		addAllowedBrickField(BrickField.TURN_LEFT_DEGREES);
		setFormulaWithBrickField(BrickField.TURN_LEFT_DEGREES, degrees);
	}

	@Override
	public int getRequiredResources() {
		return getFormulaWithBrickField(BrickField.TURN_LEFT_DEGREES).getRequiredResources();
	}

	@Override
	public View getView(Context context, int brickId, BaseAdapter baseAdapter) {
		if (animationState) {
			return view;
		}
		view = View.inflate(context, R.layout.brick_turn_left, null);
		view = BrickViewProvider.setAlphaOnView(view, alphaValue);

		setCheckboxView(R.id.brick_turn_left_checkbox);
		TextView textDegrees = (TextView) view.findViewById(R.id.brick_turn_left_prototype_text_view);
		TextView editDegrees = (TextView) view.findViewById(R.id.brick_turn_left_edit_text);
		getFormulaWithBrickField(BrickField.TURN_LEFT_DEGREES).setTextFieldId(R.id.brick_turn_left_edit_text);
		getFormulaWithBrickField(BrickField.TURN_LEFT_DEGREES).refreshTextField(view);

		textDegrees.setVisibility(View.GONE);
		editDegrees.setVisibility(View.VISIBLE);
		editDegrees.setOnClickListener(this);
		return view;
	}

	@Override
	public View getPrototypeView(Context context) {
		prototypeView = View.inflate(context, R.layout.brick_turn_left, null);
		TextView textDegrees = (TextView) prototypeView.findViewById(R.id.brick_turn_left_prototype_text_view);
		textDegrees.setText(Utils.getNumberStringForBricks(BrickValues.TURN_DEGREES));
		return prototypeView;
	}

	@Override
	public List<SequenceAction> addActionToSequence(Sprite sprite, SequenceAction sequence) {
		sequence.addAction(sprite.getActionFactory().createTurnLeftAction(sprite,
				getFormulaWithBrickField(BrickField.TURN_LEFT_DEGREES)));
		return null;
	}

	@Override
	public void showFormulaEditorToEditFormula(View view) {
		FormulaEditorFragment.showFragment(view, this, BrickField.TURN_LEFT_DEGREES);
	}

	@Override
	public void updateReferenceAfterMerge(Scene into, Scene from) {
	}
}