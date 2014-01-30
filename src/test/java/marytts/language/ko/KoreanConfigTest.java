
package marytts.language.ko;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import marytts.config.LanguageConfig;
import marytts.config.MaryConfig;
import marytts.exceptions.MaryConfigurationException;
import marytts.language.ko.KoreanConfig;

import org.junit.Test;


public class KoreanConfigTest {

	@Test
	public void isNotMainConfig() throws MaryConfigurationException {
		MaryConfig m = new KoreanConfig();
		assertFalse(m.isMainConfig());
	}
	
	@Test
	public void canGet() {
		MaryConfig m = MaryConfig.getLanguageConfig(new Locale("ko"));
		assertNotNull(m);
		assertTrue(((LanguageConfig)m).getLocales().contains(new Locale("ko")));
	}
	
	
	@Test
	public void hasFrenchLocale() throws MaryConfigurationException {
		LanguageConfig e = new KoreanConfig();
		assertTrue(e.getLocales().contains(new Locale("ko")));
	}
}
