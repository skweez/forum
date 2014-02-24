package net.skweez.forum.server.js;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.skweez.forum.config.Config;
import net.skweez.forum.config.Setting;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.JSError;
import com.google.javascript.jscomp.SourceFile;

/**
 * The servlet that serves js files.
 * 
 * @author mks
 */
@Path("js")
public class JsServlet {

	/** The compiled Javascript. */
	private String compiledJavascript;

	/** @return the compiled Javascript. */
	@GET
	@Path("skweez.js")
	@Produces("application/javascript")
	public String getCompiledJavascript() {
		if (alwaysRecompile()) {
			return compileJavascript();
		} else if (compiledJavascript == null) {
			compiledJavascript = compileJavascript();
		}
		return compiledJavascript;
	}

	/**
	 * Checks if the Javascript sources should be compiled on every request.
	 * 
	 * @return <code>true</code> if the option
	 *         {@link Setting#SERVER_ALWAYS_COMPILE_JS} is set to
	 *         <code>'true'</code> (ignoring case).
	 */
	private boolean alwaysRecompile() {
		return Config.getBoolean(Setting.SERVER_ALWAYS_COMPILE_JS);
	}

	/**
	 * Compile js files with the google closure-compiler.
	 * 
	 * @return the compiled js source
	 */
	protected String compileJavascript() {
		Compiler compiler = new Compiler();

		CompilerOptions options = new CompilerOptions();
		CompilationLevel.SIMPLE_OPTIMIZATIONS
				.setOptionsForCompilationLevel(options);

		List<SourceFile> extern = new ArrayList<SourceFile>();

		File jsSourceDirectory = new File("src/main/js");
		List<SourceFile> modules = new ArrayList<SourceFile>();

		Collection<File> files = FileUtils
				.listFiles(jsSourceDirectory,
						FileFilterUtils.suffixFileFilter("js"),
						TrueFileFilter.INSTANCE);

		for (File file : files) {
			modules.add(SourceFile.fromFile(file));
		}

		compiler.compile(extern, modules, options);

		for (JSError message : compiler.getWarnings()) {
			System.err.println("Warning message: " + message.toString());
		}

		for (JSError message : compiler.getErrors()) {
			System.err.println("Error message: " + message.toString());
		}

		return compiler.toSource();
	}
}