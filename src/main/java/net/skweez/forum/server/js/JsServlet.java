package net.skweez.forum.server.js;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
 * 
 */
@Path("js")
public class JsServlet {

	/**
	 * @return the compiled js
	 */
	@GET
	@Path("skweez.js")
	@Produces("application/javascript")
	public String getCompiledJavascript() {
		return compileJavascript();
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