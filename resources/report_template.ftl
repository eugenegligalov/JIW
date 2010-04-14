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
<#list issues as x>
	<TR>${x}<#if x_has_next></TR></#if>
</#list>  
 </TR>
</TABLE>


</BODY>
</HTML>
