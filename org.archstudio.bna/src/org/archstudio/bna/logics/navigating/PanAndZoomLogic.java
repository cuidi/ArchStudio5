package org.archstudio.bna.logics.navigating;

import static org.archstudio.sysutils.SystemUtils.castOrNull;

import java.util.List;
import java.util.Set;

import org.archstudio.bna.IBNAView;
import org.archstudio.bna.IBNAWorld;
import org.archstudio.bna.ICoordinate;
import org.archstudio.bna.IMutableCoordinateMapper;
import org.archstudio.bna.IThing;
import org.archstudio.bna.constants.GestureType;
import org.archstudio.bna.constants.KeyType;
import org.archstudio.bna.constants.MouseType;
import org.archstudio.bna.logics.AbstractThingLogic;
import org.archstudio.bna.logics.tracking.ModelBoundsTrackingLogic;
import org.archstudio.bna.ui.IBNAAllEventsListener2;
import org.archstudio.bna.ui.IBNAKeyListener2;
import org.archstudio.bna.ui.IBNAMagnifyGestureListener;
import org.archstudio.bna.ui.IBNAMouseClickListener2;
import org.archstudio.bna.ui.IBNAMouseMoveListener2;
import org.archstudio.bna.ui.IBNAMouseWheelListener2;
import org.archstudio.bna.ui.IBNAPanGestureListener;
import org.archstudio.bna.ui.IBNATrackGestureListener;
import org.archstudio.bna.utils.BNAUtils;
import org.archstudio.bna.utils.BNAUtils2.ThingsAtLocation;
import org.archstudio.bna.utils.DefaultCoordinate;
import org.archstudio.sysutils.SystemUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.GestureEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.google.common.collect.Sets;

public class PanAndZoomLogic extends AbstractThingLogic
		implements IBNAMouseClickListener2, IBNAMouseMoveListener2, IBNAMouseWheelListener2, IBNAAllEventsListener2,
		IBNATrackGestureListener, IBNAMagnifyGestureListener, IBNAPanGestureListener, IBNAKeyListener2 {

	protected static final int PAN_BUTTON = 2; // middle button
	protected static boolean inGesture = false;
	protected static double originalScale = 1;
	protected static double minScale = Math.pow(2, -10);
	protected static double maxScale = Math.pow(2, 10);
	protected static Set<Composite> registeredComposites = Sets.newHashSet();
	protected static Listener preventScrollListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			if (!inGesture) {
				event.doit = false;
			}
		}
	};

	protected final ModelBoundsTrackingLogic boundsLogic;
	protected ICoordinate startMouseCoordinate = null;

	public PanAndZoomLogic(IBNAWorld world) {
		super(world);
		this.boundsLogic = logics.addThingLogic(ModelBoundsTrackingLogic.class);
	}

	protected static void registerView(IBNAView view) {
		final Composite composite = view.getBNAUI().getComposite();
		if (registeredComposites.add(composite)) {
			composite.addListener(SWT.MouseVerticalWheel, preventScrollListener);
			composite.addListener(SWT.MouseHorizontalWheel, preventScrollListener);
			composite.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					registeredComposites.remove(composite);
				}
			});
		}
	}

	@Override
	public void mouseDown(IBNAView view, MouseType type, MouseEvent evt, ICoordinate location,
			ThingsAtLocation thingsAtLocation) {
		BNAUtils.checkLock();

		// only handle events for the top world
		if (view.getParentView() != null) {
			return;
		}

		if (evt.button == PAN_BUTTON) {
			startMouseCoordinate = DefaultCoordinate.forLocal(new Point(evt.x, evt.y), view.getCoordinateMapper());
			Composite composite = view.getBNAUI().getComposite();
			composite.setCursor(composite.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
		}
	}

	@Override
	public void mouseUp(IBNAView view, MouseType type, MouseEvent evt, ICoordinate location,
			ThingsAtLocation thingsAtLocation) {
		BNAUtils.checkLock();

		// only handle events for the top world
		if (view.getParentView() != null) {
			return;
		}

		if (startMouseCoordinate != null) {
			startMouseCoordinate = null;
			view.getBNAUI().getComposite().setCursor(null);
		}
	}

	@Override
	public void mouseClick(IBNAView view, MouseType type, MouseEvent evt, ICoordinate location,
			ThingsAtLocation thingsAtLocation) {
	}

	@Override
	public void mouseMove(IBNAView view, MouseType type, MouseEvent evt, ICoordinate location,
			ThingsAtLocation thingsAtLocation) {
		BNAUtils.checkLock();

		// only handle events for the top world
		if (view.getParentView() != null) {
			return;
		}

		registerView(view);

		if (startMouseCoordinate != null) {
			IMutableCoordinateMapper mcm = castOrNull(view.getCoordinateMapper(), IMutableCoordinateMapper.class);
			if (mcm != null) {
				mcm.align(new Point(evt.x, evt.y), startMouseCoordinate.getWorldPoint());
			}
		}
	}

	@Override
	public void mouseHorizontalWheel(IBNAView view, MouseType type, MouseEvent evt, ICoordinate location,
			ThingsAtLocation thingsAtLocation) {
	}

	@Override
	public void mouseVerticalWheel(IBNAView view, MouseType type, MouseEvent evt, ICoordinate location,
			ThingsAtLocation thingsAtLocation) {
		BNAUtils.checkLock();

		// only handle events for the top world
		if (view.getParentView() != null) {
			return;
		}

		registerView(view);

		// only handle events for the top world
		if (view.getParentView() != null) {
			return;
		}

		if (!inGesture) {
			zoom(view, DefaultCoordinate.forLocal(new Point(evt.x, evt.y), view.getCoordinateMapper()),
					evt.count < 0 ? -1 : 1);
		}
	}

	@Override
	public void magnifyGesture(IBNAView view, GestureType type, GestureEvent e, List<IThing> t, ICoordinate location) {
		BNAUtils.checkLock();

		// only handle events for the top world
		if (view.getParentView() != null) {
			return;
		}

		IMutableCoordinateMapper mcm = castOrNull(view.getCoordinateMapper(), IMutableCoordinateMapper.class);
		if (mcm != null) {
			mcm.setLocalScaleAndAlign(SystemUtils.bound(minScale, originalScale * e.magnification, maxScale),
					location.getLocalPoint(), location.getWorldPoint());
		}
	}

	@Override
	public void beginGesture(IBNAView view, GestureType type, GestureEvent e, List<IThing> t, ICoordinate location) {
		BNAUtils.checkLock();

		// only handle events for the top world
		if (view.getParentView() != null) {
			return;
		}

		inGesture = true;
		originalScale = view.getCoordinateMapper().getLocalScale();
	}

	@Override
	public void endGesture(IBNAView view, GestureType type, GestureEvent e, List<IThing> t, ICoordinate location) {
		BNAUtils.checkLock();

		// only handle events for the top world
		if (view.getParentView() != null) {
			return;
		}

		inGesture = false;
	}

	@Override
	public void panGesture(IBNAView view, GestureType type, GestureEvent e, List<IThing> t, ICoordinate location) {
		// This seems to happen automatically using the scrollbars
	}

	@Override
	public void keyPressed(IBNAView view, KeyType type, KeyEvent e) {
		BNAUtils.checkLock();

		// only handle events for the top world
		if (view.getParentView() != null) {
			return;
		}

		if ((e.stateMask & SWT.MOD1) != 0) {
			int delta = 0;
			switch (e.character) {
			case '-':
				delta = -1;
				break;
			case '+':
			case '=':
				delta = 1;
				break;
			}
			if (delta != 0) {
				Rectangle bounds = view.getBNAUI().getComposite().getBounds();
				Point center = new Point(bounds.width / 2, bounds.height / 2);
				ICoordinate location = DefaultCoordinate.forLocal(center, view.getCoordinateMapper());
				zoom(view, location, delta);
			}
		}
	}

	@Override
	public void keyReleased(IBNAView view, KeyType type, KeyEvent e) {
	}

	private void zoom(IBNAView view, ICoordinate location, int delta) {
		IMutableCoordinateMapper mcm = castOrNull(view.getCoordinateMapper(), IMutableCoordinateMapper.class);
		if (mcm != null) {
			double oldScale = mcm.getLocalScale();
			double oldPower = Math.log(oldScale) / Math.log(Math.sqrt(2));
			double newPower = oldPower + delta;
			double newScale = Math.pow(Math.sqrt(2), newPower);
			mcm.setLocalScaleAndAlign(SystemUtils.bound(minScale, newScale, maxScale), location.getLocalPoint(),
					location.getWorldPoint());
		}
	}
}
