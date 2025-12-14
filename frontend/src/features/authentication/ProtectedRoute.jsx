import styled from "styled-components";
import { Navigate } from "react-router-dom";
import Spinner from "../../ui/Spinner";
import { useAuthContext } from "../../context/AuthContext";

const FullPage = styled.div`
  height: 100vh;
  background-color: var(--color-grey-50);
  display: flex;
  align-items: center;
  justify-content: center;
`;

function ProtectedRoute({ children }) {
  const { user, jwt } = useAuthContext();

  // Show loading spinner while checking authentication
  // (when we have JWT but user data hasn't loaded yet)
  if (jwt && !user) {
    return (
        <FullPage>
          <Spinner />
        </FullPage>
    );
  }

  // If no JWT and no user, redirect to login
  if (!jwt && !user) {
    return <Navigate to="/login" replace />;
  }

  // User is authenticated, render protected content
  return <div>{children}</div>;
}

export default ProtectedRoute;