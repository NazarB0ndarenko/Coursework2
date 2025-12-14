import styled from "styled-components";

const StyledFooter = styled.div`
  text-align: center;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-top: 5rem;
`;

const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <StyledFooter>
      <p>This project is a final project for wed development.</p>
      <p>
        Created by <br />
        Nazar Bondarenko<br />
      </p>
    </StyledFooter>
  );
};

export default Footer;
