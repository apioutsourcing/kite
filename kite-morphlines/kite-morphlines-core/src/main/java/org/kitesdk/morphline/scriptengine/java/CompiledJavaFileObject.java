package org.kitesdk.morphline.scriptengine.java;

import java.net.URI;
import java.io.*;
import javax.tools.JavaFileObject;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.Modifier;

/**
 * CompiledJavaFileObject defines a Java class file object that can be in a folder or in a JAR.
 *
 * NOTE: This is a derivative work from pull request https://github.com/querydsl/codegen/pull/43 which
 * in turn is a derivative from an article of atamur.
 *  Original article can be found here: http://atamur.blogspot.com/2009/10/using-built-in-javacompiler-with-custom.html
 *
 * @author matteo-gallo-bb
 */
class CompiledJavaFileObject implements JavaFileObject {

  private final String binaryName;

  private final URI uri;

  private final String name;

  public CompiledJavaFileObject(String binaryName, URI uri) {
    this.uri = uri;
    this.binaryName = binaryName;
    // for FS based URI the path is not null, for JAR URI the scheme specific part is not null
    name = uri.getPath() == null ? uri.getSchemeSpecificPart() : uri.getPath();
  }

  @Override
  public URI toUri() {
    return uri;
  }

  @Override
  public InputStream openInputStream() throws IOException {
    // easy way to handle any URI!
    return uri.toURL().openStream();
  }

  @Override
  public OutputStream openOutputStream() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Reader openReader(boolean ignoreEncodingErrors) {
    throw new UnsupportedOperationException();
  }

  @Override
  public CharSequence getCharContent(boolean ignoreEncodingErrors) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Writer openWriter() {
    throw new UnsupportedOperationException();
  }

  @Override
  public long getLastModified() {
    return 0;
  }

  @Override
  public boolean delete() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Kind getKind() {
    return Kind.CLASS;
  }

  @Override
  // copied from SimpleJavaFileObject
  public boolean isNameCompatible(String simpleName, Kind kind) {
    String baseName = simpleName + kind.extension;
    return kind.equals(getKind())
            && (baseName.equals(getName())
            || getName().endsWith("/" + baseName));
  }

  @Override
  public NestingKind getNestingKind() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Modifier getAccessLevel() {
    throw new UnsupportedOperationException();
  }

  public String binaryName() {
    return binaryName;
  }


  @Override
  public String toString() {
    return "CustomJavaFileObject{" +
            "uri=" + uri +
            '}';
  }
}
