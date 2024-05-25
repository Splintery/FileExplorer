package view.bookmark;

import view.Explorer;
import view.navigation.SelectableNavigationLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class BookmarkTransferHandler extends TransferHandler {
    public static final DataFlavor SUPPORTED_DATE_FLAVOR = DataFlavor.stringFlavor;
    public Explorer parent;

    public BookmarkTransferHandler(Explorer parent) {
        this.parent = parent;
    }
    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        return support.isDataFlavorSupported(SUPPORTED_DATE_FLAVOR);
    }
    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        boolean accept = false;
        if (canImport(support)) {
            try {
                Transferable t = support.getTransferable();
                Object value = t.getTransferData(SUPPORTED_DATE_FLAVOR);
                if (value instanceof String) {
                    parent.addBookmark(value.toString());
                    accept = true;
//                    Component component = support.getComponent();
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        }
        return accept;
    }
}
