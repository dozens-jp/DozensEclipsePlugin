package net.jumperz.app.MDozens;

import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import java.io.*;
import net.jumperz.util.*;
import org.eclipse.core.runtime.Platform;
import java.net.*;
import org.eclipse.ui.console.*;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator
extends AbstractUIPlugin
implements MConstants
{
public static final String PLUGIN_ID = "net.jumperz.app.MDozens";
private static Activator plugin;
private volatile Shell shell;
private File configFile;
//--------------------------------------------------------------------------------
public synchronized void setShell( Shell s )
{
if( shell == null )
	{
	shell = s;
	//MMenuManager.getInstance().initMenus();
	}
}
//--------------------------------------------------------------------------------
public Activator()
{
}
//--------------------------------------------------------------------------------
  private MessageConsole findConsole(String name) {
      ConsolePlugin plugin = ConsolePlugin.getDefault();
      IConsoleManager conMan = plugin.getConsoleManager();
      IConsole[] existing = conMan.getConsoles();
      for (int i = 0; i < existing.length; i++)
         if (name.equals(existing[i].getName()))
            return (MessageConsole) existing[i];
      //no console found, so create a new ones
      MessageConsole myConsole = new MessageConsole(name, null);
      conMan.addConsoles(new IConsole[]{myConsole});
      return myConsole;
   }
//--------------------------------------------------------------------------------
private void setupConsole()
{
MessageConsole mc = findConsole( CONSOLE_NAME );
MessageConsoleStream out = mc.newMessageStream();
PrintStream ps = new PrintStream( out );
MAbstractLogAgent.enabled = true;
MLogServer.getInstance().setSimpleOut( ps );
}

//--------------------------------------------------------------------------------
public void start( BundleContext context )
throws Exception
{
super.start(context);
plugin = this;
setupConsole();

loadConfig();
}
//--------------------------------------------------------------------------------
public void stop( BundleContext context )
throws Exception
{
saveConfig();
plugin = null;
super.stop(context);
}
//--------------------------------------------------------------------------------
public static Activator getDefault()
{
return plugin;
}
//--------------------------------------------------------------------------------
private void saveConfig()
throws IOException
{
OutputStream out = new FileOutputStream( configFile );
try
	{
	MContext.getInstance().getProperties().store( out );
	}
finally
	{
	out.close();
	}
}
//--------------------------------------------------------------------------------
public void loadConfig()
throws IOException
{
Location location = Platform.getConfigurationLocation();
if( location != null )
	{
	URL configURL = location.getURL();
	if( configURL != null
	 && configURL.getProtocol().startsWith( "file" ) )
		{
		File platformDir = new File( configURL.getFile(), Activator.PLUGIN_ID );
		MSystemUtil.createDir( platformDir.getAbsolutePath() );
		String configFileName = platformDir.getAbsolutePath() + "/" + DEFAULT_CONFIG_FILE_NAME;
		loadConfig( configFileName );
		}
	}
else
	{
	loadConfig( "_dummy_not_exist_" );
	}
}
// --------------------------------------------------------------------------------
private void loadConfig( String configFileName )
throws IOException
{
System.out.println( configFileName );
MProperties prop = new MProperties();
configFile = new File( configFileName );
InputStream in = null;
if( configFile.exists() && configFile.isFile() )
	{
	in = new FileInputStream( configFile );
	prop.load( in );
	in.close();
	}
else
	{
	}

MContext.getInstance().setProp( prop );
}
//--------------------------------------------------------------------------------
}
