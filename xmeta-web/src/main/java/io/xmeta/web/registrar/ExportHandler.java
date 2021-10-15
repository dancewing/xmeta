package io.xmeta.web.registrar;

import org.w3c.dom.Document;

public interface ExportHandler {
    String name();

    Document process(String uuid, Document document);
}
