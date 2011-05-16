package org.archstudio.bna.logics.events;

import org.archstudio.bna.DefaultCoordinate;
import org.archstudio.bna.IBNAView;
import org.archstudio.bna.ICoordinate;
import org.archstudio.bna.IThing;
import org.archstudio.bna.IThingLogicManager;
import org.archstudio.bna.facets.IRelativeMovable;
import org.archstudio.bna.logics.AbstractThingLogic;
import org.archstudio.bna.utils.IBNAMouseListener;
import org.archstudio.bna.utils.IBNAMouseMoveListener;
import org.archstudio.bna.utils.UserEditableUtils;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class DragMoveEventsLogic extends AbstractThingLogic implements IBNAMouseListener, IBNAMouseMoveListener {

	DragMoveEvent currentEvent = null;

	public DragMoveEventsLogic(IThingLogicManager tlm) {
	}

	@Override
	public void mouseDown(IBNAView view, MouseEvent evt, Iterable<IThing> t, ICoordinate location) {
		if (evt.button == 1 && (evt.stateMask & SWT.MODIFIER_MASK) == 0) {
			Iterable<IThing> editableThings = Iterables.filter(t, new Predicate<IThing>() {
				@Override
				public boolean apply(IThing input) {
					return input instanceof IRelativeMovable
							&& UserEditableUtils.isEditableForAllQualities(input, IRelativeMovable.USER_MAY_MOVE);
				}
			});
			if (!Iterables.isEmpty(editableThings)) {
				view.setCursor(SWT.CURSOR_SIZEALL);
				fireDragStartedEvent(currentEvent = new DragMoveEvent(view, evt, editableThings,
						DefaultCoordinate.forLocal(new Point(evt.x, evt.y), view.getCoordinateMapper())));

			}
		}
	}

	@Override
	public void mouseMove(IBNAView view, MouseEvent evt, Iterable<IThing> t, ICoordinate location) {
		if (currentEvent != null) {
			fireDragMoveEvent(currentEvent = new DragMoveEvent(currentEvent, DefaultCoordinate.forLocal(new Point(
					evt.x, evt.y), view.getCoordinateMapper())));
		}
	}

	@Override
	public void mouseUp(IBNAView view, MouseEvent evt, Iterable<IThing> t, ICoordinate location) {
		if (currentEvent != null) {
			view.setCursor(SWT.CURSOR_ARROW);
			fireDragFinishedEvent(currentEvent = new DragMoveEvent(currentEvent, DefaultCoordinate.forLocal(new Point(
					evt.x, evt.y), view.getCoordinateMapper())));
			currentEvent = null;
		}
	}

	protected void fireDragStartedEvent(DragMoveEvent evt) {
		for (IDragMoveListener logic : getBNAWorld().getThingLogicManager().getThingLogics(IDragMoveListener.class)) {
			logic.dragStarted(evt);
		}
	}

	protected void fireDragMoveEvent(DragMoveEvent evt) {
		for (IDragMoveListener logic : getBNAWorld().getThingLogicManager().getThingLogics(IDragMoveListener.class)) {
			logic.dragMoved(evt);
		}
	}

	protected void fireDragFinishedEvent(DragMoveEvent evt) {
		for (IDragMoveListener logic : getBNAWorld().getThingLogicManager().getThingLogics(IDragMoveListener.class)) {
			logic.dragFinished(evt);
		}
	}
}
