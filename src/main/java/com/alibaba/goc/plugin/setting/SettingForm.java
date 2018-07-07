package com.alibaba.goc.plugin.setting;

import a.f.F;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.ide.util.TreeFileChooser;
import com.intellij.ide.util.TreeFileChooserFactory;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.PsiFileSystemItemUtil;
import com.intellij.util.FileContentUtil;
import org.apache.commons.io.filefilter.FileFileFilter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingForm {
    private JPanel panel1;
    private JTextField bookSlug;
    private JTextField apiKey;
    private JButton button1;
    private JButton button2;
    private TextFieldWithBrowseButton textFieldWithBrowseButton;

    public SettingForm(Project project) {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //文件选择器-可以选择文件夹
                FileChooserDescriptor singleFileDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor();
                VirtualFile virtualFile = FileChooser.chooseFile(singleFileDescriptor, project, null);
                System.out.println(virtualFile);
//                PackageChooserDialog packageChooserDialog = new PackageChooserDialog("选择包", project);
//                packageChooserDialog.show();
//                PsiPackage selectedPackage = packageChooserDialog.getSelectedPackage();
//                System.out.println(selectedPackage);
            }
        });

        textFieldWithBrowseButton.addBrowseFolderListener("Point to Fossil command line", null, project,
                new FileChooserDescriptor(true, false, false, false, false, false));

    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public JTextField getBookSlug() {
        return bookSlug;
    }

    public void setBookSlug(JTextField bookSlug) {
        this.bookSlug = bookSlug;
    }

    public JTextField getApiKey() {
        return apiKey;
    }

    public void setApiKey(JTextField apiKey) {
        this.apiKey = apiKey;
    }

    private SettingProvider settingProvider;

    public JComponent createPanel(SettingProvider settingProvider) {

        this.settingProvider = settingProvider;
        return panel1;
    }

    public boolean isModified() {
        return !Comparing.equal(apiKey.getText(), settingProvider.getApikey())
                || !Comparing.equal(bookSlug.getText(), settingProvider.getSlug())
                || !Comparing.equal(textFieldWithBrowseButton.getTextField().getText(), settingProvider.getSlug());
    }

    public void apply() {
        String text = textFieldWithBrowseButton.getTextField().getText();
        settingProvider.setApikey(apiKey.getText());
        settingProvider.setSlug(bookSlug.getText());
    }

    public void reset() {
        apiKey.setText(settingProvider.getApikey());
        bookSlug.setText(settingProvider.getSlug());
    }


}
