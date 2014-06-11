package com.example.xposedsample.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.URL;
import java.security.Permission;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;

public class TosConnection extends HttpsURLConnection  {
    private final HttpsURLConnection inner;

    public TosConnection(HttpsURLConnection inner) {
        super(inner.getURL());
        this.inner = inner;
    }

    @Override
    public void connect() throws IOException {
        inner.connect();
    }

    @Override
    public boolean getAllowUserInteraction() {
        return inner.getAllowUserInteraction();
    }

    @Override
    public Object getContent() throws IOException {
        return inner.getContent();
    }

    @Override
    public Object getContent(Class[] types) throws IOException {
        return inner.getContent(types);
    }

    @Override
    public String getContentEncoding() {
        return inner.getContentEncoding();
    }

    @Override
    public int getContentLength() {
        return inner.getContentLength();
    }

    @Override
    public String getContentType() {
        return inner.getContentType();
    }

    @Override
    public long getDate() {
        return inner.getDate();
    }

    @Override
    public boolean getDefaultUseCaches() {
        return inner.getDefaultUseCaches();
    }

    @Override
    public boolean getDoInput() {
        return inner.getDoInput();
    }

    @Override
    public boolean getDoOutput() {
        return inner.getDoOutput();
    }

    @Override
    public long getExpiration() {
        return inner.getExpiration();
    }

    @Override
    public String getHeaderField(int pos) {
        return inner.getHeaderField(pos);
    }

    @Override
    public Map<String, List<String>> getHeaderFields() {
        return inner.getHeaderFields();
    }

    @Override
    public Map<String, List<String>> getRequestProperties() {
        return inner.getRequestProperties();
    }

    @Override
    public void addRequestProperty(String field, String newValue) {
        inner.addRequestProperty(field, newValue);
    }

    @Override
    public String getHeaderField(String key) {
        return inner.getHeaderField(key);
    }

    @Override
    public long getHeaderFieldDate(String field, long defaultValue) {
        return inner.getHeaderFieldDate(field, defaultValue);
    }

    @Override
    public int getHeaderFieldInt(String field, int defaultValue) {
        return inner.getHeaderFieldInt(field, defaultValue);
    }

    @Override
    public String getHeaderFieldKey(int posn) {
        return inner.getHeaderFieldKey(posn);
    }

    @Override
    public long getIfModifiedSince() {
        return inner.getIfModifiedSince();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inner.getInputStream();
    }

    @Override
    public long getLastModified() {
        return inner.getLastModified();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return inner.getOutputStream();
    }

    @Override
    public Permission getPermission() throws IOException {
        return inner.getPermission();
    }

    @Override
    public String getRequestProperty(String field) {
        return inner.getRequestProperty(field);
    }

    @Override
    public URL getURL() {
        return inner.getURL();
    }

    @Override
    public boolean getUseCaches() {
        return inner.getUseCaches();
    }

    @Override
    public void setAllowUserInteraction(boolean newValue) {
        inner.setAllowUserInteraction(newValue);
    }

    @Override
    public void setDefaultUseCaches(boolean newValue) {
        inner.setDefaultUseCaches(newValue);
    }

    @Override
    public void setDoInput(boolean newValue) {
        inner.setDoInput(newValue);
    }

    @Override
    public void setDoOutput(boolean newValue) {
        inner.setDoOutput(newValue);
    }

    @Override
    public void setIfModifiedSince(long newValue) {
        inner.setIfModifiedSince(newValue);
    }

    @Override
    public void setRequestProperty(String field, String newValue) {
        inner.setRequestProperty(field, newValue);
    }

    @Override
    public void setUseCaches(boolean newValue) {
        inner.setUseCaches(newValue);
    }

    @Override
    public void setConnectTimeout(int timeout) {
        inner.setConnectTimeout(timeout);
    }

    @Override
    public int getConnectTimeout() {
        return inner.getConnectTimeout();
    }

    @Override
    public void setReadTimeout(int timeout) {
        inner.setReadTimeout(timeout);
    }

    @Override
    public int getReadTimeout() {
        return inner.getReadTimeout();
    }

    @Override
    public void disconnect() {
        inner.disconnect();
    }

    @Override
    public InputStream getErrorStream() {
        return inner.getErrorStream();
    }

    @Override
    public String getRequestMethod() {
        return inner.getRequestMethod();
    }

    @Override
    public int getResponseCode() throws IOException {
        return inner.getResponseCode();
    }

    @Override
    public String getResponseMessage() throws IOException {
        return inner.getResponseMessage();
    }

    @Override
    public void setRequestMethod(String method) throws ProtocolException {
        inner.setRequestMethod(method);
    }

    @Override
    public boolean usingProxy() {
        return inner.usingProxy();
    }

    @Override
    public boolean getInstanceFollowRedirects() {
        return inner.getInstanceFollowRedirects();
    }

    @Override
    public void setInstanceFollowRedirects(boolean followRedirects) {
        inner.setInstanceFollowRedirects(followRedirects);
    }

    @Override
    public void setFixedLengthStreamingMode(int contentLength) {
        inner.setFixedLengthStreamingMode(contentLength);
    }

    @Override
    public void setChunkedStreamingMode(int chunkLength) {
        inner.setChunkedStreamingMode(chunkLength);
    }

    @Override
    public String getCipherSuite() {
        return inner.getCipherSuite();
    }

    @Override
    public Certificate[] getLocalCertificates() {
        return inner.getLocalCertificates();
    }

    @Override
    public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
        return inner.getServerCertificates();
    }

    @Override
    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        return inner.getPeerPrincipal();
    }

    @Override
    public Principal getLocalPrincipal() {
        return inner.getLocalPrincipal();
    }

    @Override
    public void setHostnameVerifier(HostnameVerifier v) {
        inner.setHostnameVerifier(v);
    }

    @Override
    public HostnameVerifier getHostnameVerifier() {
        return inner.getHostnameVerifier();
    }

    @Override
    public void setSSLSocketFactory(SSLSocketFactory sf) {
        inner.setSSLSocketFactory(sf);
    }

    @Override
    public SSLSocketFactory getSSLSocketFactory() {
        return inner.getSSLSocketFactory();
    }
}
