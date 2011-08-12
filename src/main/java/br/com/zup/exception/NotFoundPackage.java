package br.com.zup.exception;

public class NotFoundPackage extends Exception {

	private static final long serialVersionUID = 549021669319923660L;

	public NotFoundPackage(String format) {
		super(format);
	}
}
