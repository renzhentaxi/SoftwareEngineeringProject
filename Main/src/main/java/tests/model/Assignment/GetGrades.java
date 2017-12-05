//package tests.model.Assignment;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import services.login.interfaces.ILoginToken;
//
//import java.util.Iterator;
//
//public class GetGrades
//{
//    static Iterator<ILoginToken> validRequester_provider()
//    {
//
//    }
//
//    static Iterator<ILoginToken> invalidRequester_provider()
//    {
//    }
//
//    @ParameterizedTest
//    @MethodSource(names = "validRequester_provider")
//    void validRequester_returnsGrades(ILoginToken validRequester)
//    {
//
//    }
//
//
//    @ParameterizedTest
//    @MethodSource(names = "invalidRequester_provider")
//    void invalidRequester_throwsNoPermissionException(ILoginToken invalidRequester)
//    {
//
//    }
//
//    @Test
//    void ungradedGradesAreReturnedAsNegativeOne()
//    {
//
//    }
//
//    @Test
//    void attemptsToEditGrades_throwsUnsupportedOperation()
//    {
//
//    }
//
//    @Test
//    void returnsAllGrades()
//    {
//
//    }
//}
