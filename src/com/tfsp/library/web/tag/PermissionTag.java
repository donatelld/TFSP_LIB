/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package com.tfsp.library.web.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.tfsp.library.web.context.SessionContext;
import com.tfsp.library.web.user.object.UserInfo;


/**
 *
 * @author shmilycharlene
 */

public class PermissionTag extends BodyTagSupport {
	private static final long	serialVersionUID	= 1L;
//	private Logger logger = LoggerFactory.getLogger(getClass());
//    private Map<String, Permission> permissionMap;
    private String link;
    private String type;
//    private String parentLink;
    private Boolean reverse = false;
    
    /** 
     * Creates new instance of tag handler 
     */
    public PermissionTag() {
        super();
    }
    /**
     * Method called from doStartTag().
     * Fill in this method to perform other operations from doStartTag().
     */
    private void otherDoStartTagOperations() {
        
//        permissionMap = userInfo.getPermissionTree();
    }
    
    /**
     * Method called from doEndTag()
     * Fill in this method to perform other operations from doEndTag().
     */
    private void otherDoEndTagOperations() {
    }
    
    /**
     * Fill in this method to process the body content of the tag.
     * You only need to do this if the tag's BodyContent property
     * is set to "JSP" or "tagdependent."
     * If the tag's bodyContent is set to "empty," then this method
     * will not be called.
     */
    private void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
        bodyContent.writeOut(out);
        
        bodyContent.clearBody();
    }
    
    ////////////////////////////////////////////////////////////////
    ///                                                          ///
    ///   Tag Handler interface methods.                         ///
    ///                                                          ///
    ///   Do not modify these methods; instead, modify the       ///
    ///   methods that they call.                                ///
    ///                                                          ///
    ////////////////////////////////////////////////////////////////
    
    /**
     * This method is called when the JSP engine encounters the start tag,
     * after the attributes are processed.
     * Scripting variables (if any) have their values set here.
     * @return EVAL_BODY_BUFFERED if the JSP engine should evaluate the tag body, otherwise return SKIP_BODY.
     * This method is automatically generated. Do not modify this method.
     * Instead, modify the methods that this method calls.
     */
    @Override
    public int doStartTag() throws JspException {
        otherDoStartTagOperations();
        
        if (theBodyShouldBeEvaluated()) {
            return EVAL_BODY_BUFFERED;
        } else {
            return SKIP_BODY;
        }
    }
    
    /**
     * This method is called after the JSP engine finished processing the tag.
     * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP page, otherwise return SKIP_PAGE.
     * This method is automatically generated. Do not modify this method.
     * Instead, modify the methods that this method calls.
     */
    @Override
    public int doEndTag() throws JspException {
        otherDoEndTagOperations();
        
        if (shouldEvaluateRestOfPageAfterEndTag()) {
            return EVAL_PAGE;
        } else {
            return SKIP_PAGE;
        }
    }
    
    /**
     * This method is called after the JSP engine processes the body content of the tag.
     * @return EVAL_BODY_AGAIN if the JSP engine should evaluate the tag body again, otherwise return SKIP_BODY.
     * This method is automatically generated. Do not modify this method.
     * Instead, modify the methods that this method calls.
     */
    @Override
    public int doAfterBody() throws JspException {
        try {
            // This code is generated for tags whose bodyContent is "JSP"
            BodyContent bodyCont = getBodyContent();
            JspWriter out = bodyCont.getEnclosingWriter();
            
            writeTagBodyContent(out, bodyCont);
        } catch (Exception ex) {
            handleBodyContentException(ex);
        }
        
        if (theBodyShouldBeEvaluatedAgain()) {
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }
    
    /**
     * Handles exception from processing the body content.
     */
    private void handleBodyContentException(Exception ex) throws JspException {
        // Since the doAfterBody method is guarded, place exception handing code here.
        throw new JspException("Error in PermissionTag tag", ex);
    }
    
    /**
     * Fill in this method to determine if the rest of the JSP page
     * should be generated after this tag is finished.
     * Called from doEndTag().
     */
    private boolean shouldEvaluateRestOfPageAfterEndTag()  {
        return true;
    }
    
    /**
     * Fill in this method to determine if the tag body should be evaluated
     * again after evaluating the body.
     * Use this method to create an iterating tag.
     * Called from doAfterBody().
     */
    private boolean theBodyShouldBeEvaluatedAgain() {
        return false;
    }

    private boolean theBodyShouldBeEvaluated() {
    	UserInfo userInfo = SessionContext.UserCenter.getLoggedUser((HttpServletRequest)pageContext.getRequest());
        boolean hasPermission = userInfo.matches(link, type);
//        logger.debug("Detect permission for URI:{0}, accessable:{1}", link, hasPermission);
        if(reverse != null && reverse.booleanValue()){
        	return !hasPermission;
        }
        return hasPermission;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public void setParentLink(String parentLink){
//        this.parentLink = parentLink;
//    }
    
    public Boolean isReverse() {
		return reverse;
	}
	public void setReverse(Boolean reverse) {
		this.reverse = reverse;
	}
}
