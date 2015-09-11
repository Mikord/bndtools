package test;

import java.io.*;
import java.util.regex.*;

import junit.framework.*;
import aQute.bnd.build.*;
import aQute.bnd.osgi.*;
import aQute.bnd.osgi.Processor.FileLine;
import aQute.lib.io.*;

public class LocationTest extends TestCase {
	Workspace		ws;
	private File	tmp;

	public void setUp() throws Exception {
		tmp = IO.getFile("generated/tmp");
		IO.copy(IO.getFile("testresources/ws-location"), tmp);
		ws = new Workspace(tmp);

	}

	public void tearDown() throws Exception {
		IO.delete(tmp);
	}

	public void testMerged() throws Exception {
		Project project = ws.getProject("locationtest");

		FileLine fl = project.getHeader("-merged", "BAZ");
		assertNotNull(fl);
		assertEquals(project.getPropertiesFile().getAbsolutePath(), fl.file.getAbsolutePath());
		assertEquals(18, fl.line);
		assertEquals(167, fl.start);
		assertEquals(170, fl.end);

	}

	public void testProjectHeaderClauses() throws Exception {
		Project project = ws.getProject("locationtest");
		assertNotNull(project);

		FileLine fl = project.getHeader("-inprojectsep", "BAZ");
		assertNotNull(fl);
		assertEquals(project.getPropertiesFile().getAbsolutePath(), fl.file.getAbsolutePath());
		assertEquals(10, fl.line);
		assertEquals(104, fl.start);
		assertEquals(107, fl.end);

		fl = project.getHeader("-inproject", "BAZ");
		assertNotNull(fl);
		assertEquals(project.getPropertiesFile().getAbsolutePath(), fl.file.getAbsolutePath());
		assertEquals(3, fl.line);
		assertEquals(23, fl.start);
		assertEquals(26, fl.end);

	}

	public void testHeaderInSub() throws Exception {
		Project project = ws.getProject("locationtest");
		Builder builder = project.getSubBuilders().iterator().next();
		assertNotNull(builder);

		FileLine fl = builder.getHeader("-inprojectsep", "BAZ");
		assertNotNull(fl);
		assertEquals(project.getPropertiesFile().getAbsolutePath(), fl.file.getAbsolutePath());
		assertEquals(10, fl.line);
		assertEquals(104, fl.start);
		assertEquals(107, fl.end);

	}

	public void testBasic() throws Exception {
		Project project = ws.getProject("p1");
		assertNotNull(project);

		ProjectBuilder sub1 = project.getSubBuilder("sub1");
		assertNotNull(sub1);

		ProjectBuilder sub2 = project.getSubBuilder("sub2");
		assertNotNull(sub2);

		assertTrue(find(sub1, "sub1", "p1/sub1.bnd", 4));
		assertTrue(find(sub1, "bnd.bnd", "p1/bnd.bnd", 4));
		assertTrue(find(project, "bnd.bnd", "p1/bnd.bnd", 4));
		assertTrue(find(sub1, "i1", "p1/i1.bnd", 2));
		assertTrue(find(project, "i1", "p1/i1.bnd", 2));
		assertTrue(find(sub1, "i2", "p1/i2.bnd", 2));
		assertTrue(find(project, "i2", "p1/i2.bnd", 2));
		assertTrue(find(sub2, "sub2", "p1/sub2.bnd", 3));
		assertTrue(find(sub2, "bnd.bnd", "p1/bnd.bnd", 4));
		assertTrue(find(sub2, "workspace", "cnf/build.bnd", 6));
		assertTrue(find(project, "workspace", "cnf/build.bnd", 6));
		assertTrue(find(project.getWorkspace(), "workspace", "cnf/build.bnd", 6));
	}

	private boolean find(Processor p, String what, String file, int line) throws Exception {
		Pattern pattern = Pattern.compile("^" + what, Pattern.MULTILINE);
		Processor.FileLine fl = p.getHeader(pattern);
		assertNotNull(fl);
		assertTrue(fl.file.getAbsolutePath().replace(File.separatorChar, '/').endsWith(file));
		assertEquals(line, fl.line);
		return true;
	}

}
