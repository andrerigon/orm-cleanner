package br.com.zup.file;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

public class JavaCodeFormatter {

	private static final Map<Object, Object> OPTIONS = java6options();
	
	Log log = LogFactory.getLog(getClass());

	public String format(String code) {

		try {
			IDocument document = new Document(code);
			textEdit(code).apply(document);
			return document.get();
		} catch (Exception e) {
			log.error("could not format code", e);
			return code;
		}

	}

	private TextEdit textEdit(String code) {
		final CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(OPTIONS);

		// retrieve the source to format
		final TextEdit edit = codeFormatter.format(CodeFormatter.K_COMPILATION_UNIT, // format
																						// a
																						// compilation
																						// unit
				code, // source to format
				0, // starting position
				code.length(), // length
				0, // initial indentation
				System.getProperty("line.separator") // line separator
				);
		return edit;
	}

	private static Map<Object, Object> java6options() {
		@SuppressWarnings("unchecked")
		Map<Object, Object> options = DefaultCodeFormatterConstants.getEclipseDefaultSettings();

		// initialize the compiler settings to be able to format 1.5 code
		options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_6);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_6);
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_6);

		// change the option to wrap each enum constant on a new line
		options.put(DefaultCodeFormatterConstants.FORMATTER_ALIGNMENT_FOR_ENUM_CONSTANTS, DefaultCodeFormatterConstants
				.createAlignmentValue(true, DefaultCodeFormatterConstants.WRAP_ONE_PER_LINE,
						DefaultCodeFormatterConstants.INDENT_ON_COLUMN));
		return options;
	}

}
