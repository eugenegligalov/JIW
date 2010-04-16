<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 TRANSITIONAL//EN">

<HTML>
<HEAD>
 <TITLE>JIW Report</TITLE>
</HEAD>
<BODY>
<TABLE>
 <TR>
  <TH>ID</TD>
  <TH>Created</TH>
  <TH>Updated</TH>
  <TH>Duedate</TH>
  <TH>Priority</TH>
  <TH>Status</TH>
  <TH>Summary</TH>
 </TR>
<#list sections as section>
  <TR><TD><B>${section.getFilter().getName()}</B></TD></TR>
  <#list section.getRows() as row>
  <TR>
    <TD><A HREF="${row.getJiraIssueUrl()}">${row.getKey()}</A></TD>
    <TD>${row.getCreatedDate()}</TD>
    <TD>${row.getLastUpdateDate()}</TD>
    <TD>${row.getDueDate()}</TD>
    <TD>${row.getPriority()}</TD>
    <TD>${row.getStatus()}</TD>
    <TD>${row.getSummary()}</TD>
  </TR>
  </#list>
</#list>
</TABLE>
</BODY>
</HTML>
