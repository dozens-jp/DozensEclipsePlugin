package net.jumperz.app.MDozens.view;

import org.eclipse.ui.part.ViewPart;
import org.eclipse.jface.action.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Composite;
import java.util.*;
import java.io.*;
import net.jumperz.app.MDozens.*;
import net.jumperz.util.*;

public abstract class MAbstractView
extends ViewPart
implements MConstants, Listener
{
protected Shell shell;
private String windowName;
protected FormLayout	formLayout;
protected static FormData buttonFormData1, buttonFormData2, buttonFormData3;
protected boolean initialized = false;
protected Composite parent;
protected MenuManager menuManager;
protected IMenuManager dropDownMenu;
protected IToolBarManager toolBar;
protected IActionBars actionBars;

private MAbstractLogAgent logAgent = new MAbstractLogAgent(){};

static
{
}
//--------------------------------------------------------------------------------
public MAbstractView()
{
}
//--------------------------------------------------------------------------------
protected boolean isActive()
{
return getSite().getPage().getActivePart().equals( this );
}
// --------------------------------------------------------------------------------
public void log( int logLevel, Object message )
{
logAgent.log( logLevel, message );
}
// --------------------------------------------------------------------------------
public void info( Object message )
{
log( MLogServer.log_info, message );
}
// --------------------------------------------------------------------------------
public void warn( Object message )
{
log( MLogServer.log_warn, message );
}
// --------------------------------------------------------------------------------
public void debug( Object message )
{
log( MLogServer.log_debug, message );
}
//--------------------------------------------------------------------------------
protected void setActionImage( Action action, String imageFileName )
{
Image image = MUtil.getImage( parent.getShell().getDisplay(), imageFileName );
action.setImageDescriptor( ImageDescriptor.createFromImage( image ) );
}
//--------------------------------------------------------------------------------
protected void addActionToDropDownMenu( Action action )
{
dropDownMenu.add( action );
}
//--------------------------------------------------------------------------------
protected void addActionToToolBar( Action action )
{
toolBar.add( action );
}
//--------------------------------------------------------------------------------
protected void initAction( Action action, String imageFileName )
{
initAction( action, imageFileName, null );
}
//--------------------------------------------------------------------------------
protected void initAction( Action action, String imageFileName, MenuManager menuManager )
{
try
	{
	if( imageFileName != null )
		{
		setActionImage( action, imageFileName );	
		}
	addActionToDropDownMenu( action );
	addActionToToolBar( action );
	
	if( menuManager != null )
		{
		menuManager.add( action );	
		}
	}
catch( Exception e )
	{
	//eventManager.fireErrorEvent( e );
	}
}
//--------------------------------------------------------------------------------
public void createPartControl( Composite parent )
{
this.parent = parent;
shell = parent.getShell();

if( Activator.getDefault() != null )
	{
	Activator.getDefault().setShell( shell );
	}

formLayout = new FormLayout();

actionBars = getViewSite().getActionBars();
toolBar = actionBars.getToolBarManager();
dropDownMenu = actionBars.getMenuManager();

init2();

initialized = true;
}
// --------------------------------------------------------------------------------
protected void init2()
{
}
// --------------------------------------------------------------------------------
public final void handleEvent( Event event )
{
handleEvent2( event );
}
// --------------------------------------------------------------------------------
protected void handleEvent2( Event event )
{
}
//--------------------------------------------------------------------------------
}
