package net.skweez.forum.server.js;
 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlet.DefaultServlet;

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
@SuppressWarnings("serial")
public class JsServlet extends DefaultServlet {

	@Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/javascript;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(compileJavascript());
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
 
        File jsSourceDirectory = new File("js-src");
        List<SourceFile> modules = new ArrayList<SourceFile>();
        for (File file : jsSourceDirectory.listFiles()) {
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